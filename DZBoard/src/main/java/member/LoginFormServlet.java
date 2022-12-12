package member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/member/loginForm")
public class LoginFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = null;
		for (Cookie cookie: request.getCookies()) {
			if ("id".equals(cookie.getName())) {
				id = cookie.getValue();
			}
		}
		
		if (id != null) {
			request.setAttribute("cookie_id", id);
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/member/login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
