package server;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/app/*")
public class MapperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String key = request.getPathInfo();
		System.out.println("key: " + key);
		@SuppressWarnings("unchecked")
		Map<String, Class<? extends Action>> actionMap = (Map<String, Class<? extends Action>>) getServletContext().getAttribute("actionMap");
		Class<? extends Action> cls = actionMap.get(key);
		if (cls != null) {
			try {
				cls.getDeclaredConstructor().newInstance().execute(request, response);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException | ServletException | IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("cls is null");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
