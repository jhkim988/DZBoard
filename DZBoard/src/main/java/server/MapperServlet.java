package server;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.json.JSONObject;

public class MapperServlet extends HttpServlet {
	private static final long serialVersionUID = 8104507226903949015L;
	
	private Map<String, Object> classNameToObjectMap = new HashMap<>();
	private Map<String, Object> objectMap = new HashMap<>();
	private Map<String, Method> methodMap = new HashMap<>();

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println(config.getServletContext().getResourcePaths("/WEB-INF/classes"));
		String actionNames = config.getInitParameter("actionNames");
		Objects.requireNonNull(actionNames);
		String[] actionInfos = actionNames.split("\n");
		try {
			for (String line : actionInfos) {
				line = line.trim();
				String[] actionInfo = line.split(":");
				Class<?> cls = Class.forName(actionInfo[1]);
				if (!classNameToObjectMap.containsKey(actionInfo[1])) {
					Object object = cls.getDeclaredConstructor().newInstance();
					classNameToObjectMap.put(actionInfo[1], object);
					objectMap.put(actionInfo[0], object);
				} else {
					objectMap.put(actionInfo[0], classNameToObjectMap.get(actionInfo[1]));
				}
				Method method = cls.getMethod(actionInfo[2], HttpServletRequest.class, HttpServletResponse.class);
				methodMap.put(actionInfo[0], method);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String key = request.getPathInfo();
		Objects.requireNonNull(key);
		System.out.println("key: " + key);

		Object obj = objectMap.get(key);
		Method method = methodMap.get(key);

		try {
			Objects.requireNonNull(obj);
			Objects.requireNonNull(method);
		} catch (NullPointerException e) {
			e.printStackTrace();
			response.sendRedirect("/DZBoard/index");
			return;
		}
		try {
			Object ret = method.invoke(obj, request, response);
			if (response.isCommitted()) {
				return;
			} else if (method.getReturnType() == String.class) {
				RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF" + (String) ret);
				System.out.println("/WEB-INF" + (String) ret);
				dispatcher.forward(request, response);
			} else if (method.getReturnType() == JSONObject.class) {
				response.setContentType("application/json;charset=utf-8");
				response.getWriter().print((JSONObject) ret);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
