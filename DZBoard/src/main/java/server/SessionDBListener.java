package server;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionActivationListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

@WebListener
public class SessionDBListener implements HttpSessionListener, HttpSessionAttributeListener, HttpSessionActivationListener, HttpSessionBindingListener {
	private int numVisit = 0;
	private int numLogin = 0;
	
	
	/* HttpSessionListener */
	@Override
    public void sessionCreated(HttpSessionEvent se)  {
		numVisit++;
		System.out.println("numVisit: " + numVisit);
    }

	/* HttpSessionListener */
	@Override
    public void sessionDestroyed(HttpSessionEvent se)  { 
		numVisit--;
		System.out.println("numVisit: " + numVisit);
	}
	
	/* HttpSessionActivationListener */
	@Override
    public void sessionDidActivate(HttpSessionEvent se)  { 
    }

	/* HttpSessionActivationListener */
	@Override
    public void sessionWillPassivate(HttpSessionEvent se)  { 
    }
	
	/* HttpSessionAttributeListener */
	@Override
    public void attributeReplaced(HttpSessionBindingEvent se)  { 
    }

	/* HttpSessionAttributeListener */
	@Override
    public void attributeAdded(HttpSessionBindingEvent se)  { 
    }

	/* HttpSessionAttributeListener */
	@Override
    public void attributeRemoved(HttpSessionBindingEvent se)  { 
    }

	/* HttpSessionBindingListener */
	@Override
    public void valueUnbound(HttpSessionBindingEvent event)  { 
    
	}
	
	/* HttpSessionBindingListener */
	@Override
    public void valueBound(HttpSessionBindingEvent event)  { 
    
	}

	
}
