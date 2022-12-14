package chat;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.websocket.Session;
import server.Action;
import server.RequestMapping;

@Action
public class ChatAction {
	
	@RequestMapping("/chat")
	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/chat/chat.html";
	}
	
	@RequestMapping("/chat/chatRoomList")
	public JSONObject chatRoomList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		return jsonOut;
	}
}
