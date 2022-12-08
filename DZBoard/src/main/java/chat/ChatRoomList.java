package chat;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/chat/chatRoomList")
public class ChatRoomList extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Map<String, List<Session>> chatRooms = ChatSocket.getChatRooms();
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		chatRooms.entrySet().stream().forEach(entry -> { 
			JSONObject chatRoomInfo = new JSONObject();
			chatRoomInfo.put("name", entry.getKey());
			chatRoomInfo.put("size", entry.getValue().size());
			jsonArr.put(chatRoomInfo);
		});
		jsonOut.put("data", jsonArr);
		jsonOut.put("status", true);
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
