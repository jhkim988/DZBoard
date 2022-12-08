package server;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import repository.GoodBadRepository;
import repository.MemberRepository;
import repository.PostRepository;
import repository.UrlAuthRepository;

@WebListener
public class ServerListener implements ServletContextListener {
    
	public ServerListener() {
    
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	DataSource dataFactory = getDataSource();
    	MemberRepository.setDataFactory(dataFactory);
    	PostRepository.setDataFactory(dataFactory);
    	UrlAuthRepository.setDataFactory(dataFactory);
    	GoodBadRepository.setDataFactory(dataFactory);
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
