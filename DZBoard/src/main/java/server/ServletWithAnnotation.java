package server;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.json.JSONObject;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/app/*")
public class ServletWithAnnotation extends HttpServlet {
	private static final long serialVersionUID = 5144463666699386959L;

	private Map<String, Constructor<?>> urlToClass = new HashMap<>();
	private Map<String, Method> urlToMethod = new HashMap<>();
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		Set<String> setString = context.getResourcePaths("/WEB-INF/classes");
		Queue<String> que = new LinkedList<>();
		setString.stream().filter(s -> s.endsWith("/")).forEach(str -> que.add(str));
		while (!que.isEmpty()) {
			String str = que.poll();
			if (str.endsWith(".class")) {
				str = str.replace("/WEB-INF/classes/", "");
				str = str.replace(".class", "");
				String clsName = str.replace("/", ".");
				try {
					Class<?> cls = Class.forName(clsName);
					Action action = cls.getDeclaredAnnotation(Action.class);
					if (action == null) continue;
					Arrays.asList(cls.getDeclaredMethods()).stream()
						.filter(method -> method.getDeclaredAnnotation(RequestMapping.class) != null)
						.forEach(method -> {
							RequestMapping rm = method.getDeclaredAnnotation(RequestMapping.class);
							System.out.println("url: " + rm.value());
							urlToMethod.put(rm.value(), method);
							try {
								urlToClass.put(rm.value(), cls.getDeclaredConstructor());
							} catch (Exception e) {
								e.printStackTrace();
							}
						});
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
			
			if (str.endsWith("/")) {
				context.getResourcePaths(str).stream()
				.filter(s -> s.endsWith("/") || s.endsWith(".class"))
				.forEach(s -> que.add(s));
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String path = request.getPathInfo();
		System.out.println("ServletWithAnnotation: " + path);
		try {
			if (urlToClass.get(path) == null) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/app/index");
				dispatcher.forward(request, response);
				return;
			}
			Object obj = urlToClass.get(path).newInstance();
			Method method = urlToMethod.get(path);
			Object ret = method.invoke(obj, request, response);
			if (response.isCommitted()) {
				return;
			} else if (method.getReturnType() == String.class) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF" + (String) ret);
				dispatcher.forward(request, response);
			} else if (method.getReturnType() == JSONObject.class) {
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print((JSONObject) ret);
			}
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
