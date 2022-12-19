package server;

import jakarta.servlet.ServletContext;

public class Utility {
	private static ServletContext servletContext;
	
	public static ServletContext getServletContext() {
		return servletContext;
	}
	
	public static void setServletContext(ServletContext context) {
		servletContext = context;
	}
}
