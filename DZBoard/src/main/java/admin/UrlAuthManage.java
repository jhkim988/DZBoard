package admin;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import entities.UrlAuth;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.UrlAuthRepository;
import server.Action;
import server.RequestMapping;
import server.Utility;

@Action
public class UrlAuthManage {
	
	@RequestMapping("/admin/createUrlAuth")
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
		
		JSONObject jsonOut = new JSONObject();
		if (commit) {
			Utility.getServletContext().setAttribute("urlAuthMap", Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth()));
			jsonOut.put("status", true);
			jsonOut.put("message", "추가!");
		} else {
			jsonOut.put("status", false);
			jsonOut.put("message", "실패!");
		}
		return jsonOut;
	}
	
	@RequestMapping("/admin/deleteUrlAuth")
	public JSONObject deleteUrlAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		String url = jsonIn.getString("url");
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		boolean commit = urlAuthRepository.deleteUrlAuthByUrl(url);
		
		JSONObject jsonOut = new JSONObject();
		
		if (commit) {
			Utility.getServletContext().setAttribute("urlAuthMap", Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth()));
			jsonOut.put("status", true);
			jsonOut.put("message", "삭제!");
		} else {
			jsonOut.put("status", false);
			jsonOut.put("message", "실패!");
		}
		return jsonOut;
	}
	
	@RequestMapping("/admin/updateUrlAuthForm")
	public String updateUrlAuthForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = request.getParameter("url");
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		UrlAuth urlAuth = urlAuthRepository.findOneUrlAuth(url);
		request.setAttribute("urlAuth", urlAuth);
		return "/resources/admin/UpdateUrlAuthForm.jsp";
	}
	
	@RequestMapping("/admin/updateUrlAuth:admin")
	public JSONObject updateUrlAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		String url = jsonIn.getString("url");
		String note = jsonIn.getString("note");
		int authority = jsonIn.getInt("authority");
		UrlAuth newUrlAuth = UrlAuth.builder()
				.url(url)
				.note(note)
				.authority(authority)
				.build();
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		boolean commit = urlAuthRepository.updateUrlAuth(newUrlAuth);
		
		JSONObject jsonOut = new JSONObject();
		
		if (commit) {
			Utility.getServletContext().setAttribute("urlAuthMap", Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth()));
			jsonOut.put("status", true);
			jsonOut.put("message", "수정!");
		} else {
			jsonOut.put("status", false);
			jsonOut.put("message", "실패!");
		}
		return jsonOut;
	}
	
	@RequestMapping("/admin/synchronizeUrlAuth")
	public String synchronizeUrlAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		Utility.getServletContext().setAttribute("urlAuthMap", Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth()));
		response.sendRedirect(request.getHeader("referer"));
		return "";
	}
	
	@RequestMapping("/admin/viewUrlAuth")
	public JSONObject viewUrlAuth(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
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
		return jsonOut;
	}
	
	@RequestMapping("/admin/addUrlAuthorityForm")
	public String addUrlAuthorityForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/admin/addUrlAuthorityForm.html";
	}
}
