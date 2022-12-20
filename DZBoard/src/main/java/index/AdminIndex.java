package index;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import server.Action;
import server.RequestMapping;

@Action
public class AdminIndex {
	
	@RequestMapping("/admin/index")
	public String index(HttpServletRequest request, HttpServletResponse respons) throws ServletException, IOException {
		return "/resources/admin/index.html";
	}
	
	@RequestMapping("/admin/memberListView")
	public String memberListView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/admin/memberListView.jsp";
	}
	
	@RequestMapping("/admin/urlAuthority")
	public String urlAuthority(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/admin/urlAuthority.html";
	}
}
