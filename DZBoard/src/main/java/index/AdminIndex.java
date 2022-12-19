package index;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminIndex {
	public String index(HttpServletRequest request, HttpServletResponse respons) throws ServletException, IOException {
		return "/resources/admin/index.html";
	}
	
	public String memberListView(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/admin/memberListView.jsp";
	}
	
	public String urlAuthority(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/admin/urlAuthority.html";
	}
}
