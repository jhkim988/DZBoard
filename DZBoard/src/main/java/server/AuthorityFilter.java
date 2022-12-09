package server;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.Member;
import repository.UrlAuthRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import admin.UrlAuth;

@WebFilter("/*")
public class AuthorityFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = -6174276011884338962L;

	private Map<String, UrlAuth> urlAuth;

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		setEncoding(httpRequest, httpResponse);
		
		String requestURI = httpRequest.getRequestURI();
		System.out.println("Filter: " + requestURI);
		if (urlAuth.containsKey(requestURI)) {
			if (!isLogined(httpRequest)) {
				if (isJSONRequest(httpRequest)) { 
					postResponse(httpResponse, false, "로그인이 필요한 서비스입니다.");
				} else {
					httpResponse.sendRedirect("/DZBoard/member/login.html");
				}
				return;
			}

			int authorityLevel = urlAuth.get(requestURI).getAuthority();
			if (getMemberAuthorityLevel(httpRequest) < authorityLevel) {
				if (isJSONRequest(httpRequest)) {
					postResponse(httpResponse, false, "권한 제한");
				} else {
					httpResponse.sendRedirect("/DZBoard/error/accessDenied.html");
				}
				return;
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		urlAuth = Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth());
	}

	public Map<String, UrlAuth> getUrlAuthMap() {
		return urlAuth;
	}

	private boolean isLogined(HttpServletRequest request) {
		return request.getSession().getAttribute("member") != null;
	}
	
	private int getMemberAuthorityLevel(HttpServletRequest request) {
		return ((Member) request.getSession().getAttribute("member")).getAuthority();
	}
	
	private boolean isJSONRequest(HttpServletRequest request) {
		String contentType = request.getContentType();
		return contentType != null && contentType.startsWith("applictaion/json");
	}
	
	private void postResponse(HttpServletResponse response, boolean status, String message) {
		try {
			PrintWriter out = response.getWriter();
			JSONObject jsonOut = new JSONObject();
			jsonOut.put("status", status);
			jsonOut.put("message", message);
			out.print(jsonOut);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void setEncoding(HttpServletRequest request, HttpServletResponse response) {
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
