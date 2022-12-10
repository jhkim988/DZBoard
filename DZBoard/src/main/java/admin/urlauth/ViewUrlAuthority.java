package admin.urlauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UrlAuthRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/admin/viewUrlAuth")
public class ViewUrlAuthority extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		PrintWriter out = response.getWriter();
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		urlAuthRepository.findAllUrlAuth().entrySet().stream()
			.forEach(entry -> {
				JSONObject j = new JSONObject();
				j.put("url", entry.getKey());
				UrlAuth urlAuth = entry.getValue();
				j.put("note", urlAuth.getNote());
				j.put("authority", urlAuth.getAuthority());
				jsonArr.put(j);
			});
		jsonOut.put("data", jsonArr);
		jsonOut.put("status", true);
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
