package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;

import admin.urlauth.UrlAuth;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.UrlAuthRepository;

@WebFilter("/*")
public class GlobalFilter implements Filter {
	private ServletContext context;
	private UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		setEncoding(httpRequest, httpResponse);
		String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
		System.out.println("Filter: " + path);
		if (path.startsWith("/resources/")) { // TODO: JSP Authority Check
			chain.doFilter(httpRequest, httpResponse);
			return;
		}
		
		@SuppressWarnings("unchecked")
		Map<String, UrlAuth> urlAuth = (Map<String, UrlAuth>) context.getAttribute("urlAuthMap");
		
		String requestURI = httpRequest.getRequestURI();
		System.out.println("requestURI: " + requestURI);
		if (urlAuth.containsKey(requestURI)) {
			if (!isLogined(httpRequest)) {
				if (isJSONRequest(httpRequest)) { 
					postResponse(httpResponse, false, "로그인이 필요한 서비스입니다.");
				} else {
					httpResponse.sendRedirect("/DZBoard/member/loginForm");
				}
				return;
			}

			int authorityLevel = urlAuth.get(requestURI).getAuthority();
			if (getMemberAuthorityLevel(httpRequest) < authorityLevel) {
				if (isJSONRequest(httpRequest)) {
					postResponse(httpResponse, false, "권한 제한");
				} else {
					httpResponse.sendRedirect("/DZBoard/pages/error/accessDenied.html");
				}
				return;
			}
		}
		
		httpRequest.getRequestDispatcher("/app" + path).forward(httpRequest, httpResponse);
	}

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		context = fConfig.getServletContext();
		context.setAttribute("urlAuthMap", Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth()));
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
