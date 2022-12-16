package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import org.json.JSONObject;

import admin.urlauth.UrlAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UrlAuthRepository;

public class UrlAuthManage {
	public JSONObject createUrlAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		return jsonOut;
	}
}
