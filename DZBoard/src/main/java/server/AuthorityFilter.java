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
import java.net.http.HttpRequest;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
public class AuthorityFilter extends HttpFilter implements Filter {
	private static final long serialVersionUID = -6174276011884338962L;

	private Map<String, Integer> urlAuth;

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String requestURI = httpRequest.getRequestURI();
		System.out.println("Filter: " + requestURI);
		if (urlAuth.containsKey(requestURI)) {
			HttpSession session = httpRequest.getSession();
			Member member = (Member) session.getAttribute("member");
			if (member == null) {
				httpResponse.sendRedirect("/DZBoard/member/login.html");
				return;
			}
				
			int authorityLevel = urlAuth.get(requestURI);
			if (member.getAuthority() < authorityLevel)
				return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		UrlAuthRepository urlAuthRepository = new UrlAuthRepository();
		urlAuth = Collections.synchronizedMap(urlAuthRepository.findAllUrlAuth());
	}

	public Map<String, Integer> getUrlAuthMap() {
		return urlAuth;
	}
}
