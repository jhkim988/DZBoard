package admin.urlauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UrlAuthRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

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
		boolean commit = urlAuthRepository.createUrlAuth(urlAuth);
		
		response.setContentType("application/json;charset=utf-8");
		JSONObject jsonOut = new JSONObject();
		PrintWriter out = response.getWriter();
		if (commit) {
			getServletContext().setAttribute("urlAuthMap", Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth()));
			jsonOut.put("status", true);
			jsonOut.put("message", "추가!");
		} else {
			jsonOut.put("status", false);
			jsonOut.put("message", "실패!");
		}
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
