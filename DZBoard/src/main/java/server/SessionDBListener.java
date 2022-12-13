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
	private static int numVisit = 0;
	private static int numLogin = 0;
	
	
	/* HttpSessionListener */
	@Override
    public void sessionCreated(HttpSessionEvent se)  {
		// 세션이 생성될 때 (브라우저로 처음 서버에 접속할 때)
		numVisit++;
		System.out.println("sessionCreated - numVisit: " + numVisit);
    }

	/* HttpSessionListener */
	@Override
    public void sessionDestroyed(HttpSessionEvent se)  { 
		// 브라우저를 종료해도 호출되지 않는다 -> 세션이 만료될 때 사라지는 듯
		numVisit--;
		System.out.println("sessionDestroyed - numVisit: " + numVisit);
	}
	/*-------------------------------*/
	/* HttpSessionActivationListener */
	/* 세션이 다른 VM 으로 마이그레이션 되면 호출된다. */
	@Override
    public void sessionDidActivate(HttpSessionEvent se)  { 
		System.out.println("sessionDidActivate");
	}

	/* HttpSessionActivationListener */
	@Override
    public void sessionWillPassivate(HttpSessionEvent se)  { 
		System.out.println("sessionWillPassivate");
	}
	/*-------------------------------*/
	/* HttpSessionAttributeListener */
	@Override
    public void attributeReplaced(HttpSessionBindingEvent se)  { 
		// 세션에 할당된 값이 바뀔 때(session.get("key"))
		System.out.println("attributeReplace");
    }

	/* HttpSessionAttributeListener */
	@Override
    public void attributeAdded(HttpSessionBindingEvent se)  { 
		// 세션에 값이 할당될 때(session.get("key"))
		numLogin++;
		System.out.println("attributeAdded - numLogin: " + numLogin);
    }

	/* HttpSessionAttributeListener */
	@Override
    public void attributeRemoved(HttpSessionBindingEvent se)  { 
		// 세션에 값이 제거될 때(session.remove("key"))
		numLogin--;
		System.out.println("attributeRemoved: - numLogin: " + numLogin);
    }
	/*-------------------------------*/
	/* HttpSessionBindingListener */
	/* HttpSessionBindingListener 구현 클래스는 @WebListener 나 web.xml 에 등록하지 않는다. */
	@Override
    public void valueBound(HttpSessionBindingEvent event)  { // binding 된 대상 세션, key 등을 얻을 수 있다.
		// HttpSessionBindingListener 를 구현한 객체가 session.setAttribute() 로 바인딩 될 때 호출된다.
		System.out.println("valueBound");
	}
	
	/* HttpSessionBindingListener */
	@Override
    public void valueUnbound(HttpSessionBindingEvent event)  { // unbinding 된 대상 세션, key 등을 얻을 수 있다.
		// HttpSessionBindingListener 를 구현한 객체가 session.removeAttribute() 로 바인딩 해제될 때 호출된다.
		System.out.println("valueUnbound");
	}
	
	
}
