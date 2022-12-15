package admin.urlauth;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UrlAuthRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

import org.json.JSONObject;

@WebServlet("/admin/updateUrlAuthForm")
public class UpdateUrlAuthFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		UrlAuth urlAuth = urlAuthRepository.findOneUrlAuth(url);
		request.setAttribute("urlAuth", urlAuth);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/admin/UpdateUrlAuthForm.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
