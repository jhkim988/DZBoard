package chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/chat/webSocket/{chatRoomName}")
public class ChatSocket {
	
	private static Map<String, List<Session>> chatRooms = Collections.synchronizedMap(new HashMap<>());
	
	@OnOpen
	public void handleOpen(@PathParam("chatRoomName") String chatRoomName, Session userSession) {
		if (!chatRooms.containsKey(chatRoomName)) {
			chatRooms.put(chatRoomName, Collections.synchronizedList(new ArrayList<>()));
		}
		List<Session> sessionUsers = chatRooms.get(chatRoomName);
		sessionUsers.add(userSession);
		sessionUsers.forEach(session -> sendMessage(session, "입장하셨습니다."));
	}
	
	@OnMessage
	public void handleMessage(@PathParam("chatRoomName") String chatRoomName, String jsonStr, Session userSession) {
		JSONObject json = new JSONObject(jsonStr);
		List<Session> sessionUsers = chatRooms.get(chatRoomName);
		sessionUsers.stream()
			.filter(session -> !session.equals(userSession))
			.forEach(session -> sendMessage(session, messageFormatter(json.getString("sender"), json.getString("message"))));
	}
	
	@OnClose
	public void handleClose(@PathParam("chatRoomName") String chatRoomName, Session userSession) {
		List<Session> sessionUsers = chatRooms.get(chatRoomName);
		sessionUsers.remove(userSession);
		sessionUsers.forEach(session -> sendMessage(session, "퇴장하셨습니다."));
		if (sessionUsers.isEmpty()) {
			chatRooms.remove(chatRoomName);
		}
	}

	public static void sendMessage(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Map<String, List<Session>> getChatRooms() {
		return chatRooms;
	}
	
	public static String messageFormatter(String sender, String message) {
		return new StringBuilder(sender)
					.append(": ")
					.append(message)
					.toString();
	}
}
