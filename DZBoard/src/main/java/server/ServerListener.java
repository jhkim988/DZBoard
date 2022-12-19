package server;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import repository.CategoryRepository;
import repository.Repository;

@WebListener
public class ServerListener implements ServletContextListener {
    
    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext context = sce.getServletContext();
    	DataSource dataFactory = getDataSource();
    	Repository.setDataFactory(dataFactory);
    	Utility.setServletContext(context);
    	context.setAttribute("allCategoryList", new CategoryRepository().findAllCategories());
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
