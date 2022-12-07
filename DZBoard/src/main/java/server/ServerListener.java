package server;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ServerListener implements ServletContextListener {
	
    public ServerListener() {
    
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext context = sce.getServletContext();
    	context.setAttribute("dataFactory", getDataSource());
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    	
    }
	
    private DataSource getDataSource() {
		try {
			Context context = new InitialContext();
			Context envContext = (Context) context.lookup("java:/comp/env");
			return (DataSource) envContext.lookup("jdbc/pro05DB");
		} catch (NamingException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("Can not lookup DataFactory");
    }
}
