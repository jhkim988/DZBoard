package admin.urlauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UrlAuthRepository;

import java.io.IOException;
import java.util.Collections;

@WebServlet("/admin/urlAuthSynchronize")
public class UrlAuthSynchronize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		getServletContext().setAttribute("urlAuthMap", Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
