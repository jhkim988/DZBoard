package server;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import admin.DeleteMemberAction;
import admin.membersearch.AllMemberSearchAction;
import admin.membersearch.MemberSearchByCreatedAtAction;
import admin.membersearch.MemberSearchByEmailAction;
import admin.membersearch.MemberSearchByIdAction;
import admin.membersearch.MemberSearchByNameAction;
import admin.membersearch.MemberSearchByPhoneAction;
import admin.membersearch.MemberSearchByUpdatedAtAction;
import admin.membersearch.MemberSerachByAuthorityAction;
import index.IndexAction;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import member.LoginAction;
import member.LoginFormAction;
import repository.CategoryRepository;
import repository.Repository;

@WebListener
public class ServerListener implements ServletContextListener {
    
    public void contextInitialized(ServletContextEvent sce)  { 
    	ServletContext context = sce.getServletContext();
    	DataSource dataFactory = getDataSource();
    	Repository.setDataFactory(dataFactory);
    	
    	context.setAttribute("actionMap", initializeActionMap());
    	context.setAttribute("allCategoryList", new CategoryRepository().findAllCategories());
    }

    public void contextDestroyed(ServletContextEvent sce)  { 

    }
	
    private Map<String, Class<? extends Action>> initializeActionMap() {
    	Map<String, Class<? extends Action>> actionMap = Collections.synchronizedMap(new HashMap<>());
    	actionMap.put("/index", IndexAction.class);
    	actionMap.put("/member/login", LoginAction.class);
    	actionMap.put("/member/loginForm", LoginFormAction.class);
    	actionMap.put("/admin/memberSearch/allMember", AllMemberSearchAction.class);
    	actionMap.put("/admin/memberSearch/createdAt", MemberSearchByCreatedAtAction.class);
    	actionMap.put("/admin/memberSearch/email", MemberSearchByEmailAction.class);
    	actionMap.put("/admin/memberSearch/id", MemberSearchByIdAction.class);
    	actionMap.put("/admin/memberSearch/name", MemberSearchByNameAction.class);
    	actionMap.put("/admin/memberSearch/phone", MemberSearchByPhoneAction.class);
    	actionMap.put("/admin/memberSearch/updatedAt", MemberSearchByUpdatedAtAction.class);
    	actionMap.put("/admin/memberSearch/authority", MemberSerachByAuthorityAction.class);
    	actionMap.put("/admin/deleteMember", DeleteMemberAction.class);
    	
    	return actionMap;
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
