package admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UrlAuthRepository;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

@WebServlet("/admin/createUrlAuth")
public class CreateUrlAuthorityServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		String url = jsonIn.getString("url");
		String note = jsonIn.getString("note");
		int authority = jsonIn.getInt("authority");
		UrlAuth urlAuth = UrlAuth.builder()
								.url(url)
								.note(note)
								.authority(authority)
								.build();
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		urlauthRepository.addUrlAuth(urlAuth);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
