package member;

import java.sql.Timestamp;

import org.json.JSONObject;

import jakarta.servlet.http.HttpSessionBindingEvent;
import jakarta.servlet.http.HttpSessionBindingListener;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Member implements HttpSessionBindingListener {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String phone;
	private Timestamp createdAt;
	private Timestamp updatedAt;
	private Integer authority;
	
	public JSONObject toJSONObject() {
		JSONObject json = new JSONObject();
		json.put("id", id);
		json.put("pwd", pwd);
		json.put("name", name);
		json.put("email", email);
		json.put("phone", phone);
		json.put("createdAt", createdAt.toString());
		json.put("updatedAt", updatedAt.toString());
		json.put("authority", authority);
		return json;
	}
	
	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		System.out.println("Login!");
	}
	
	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		System.out.println("Logout!");
	}
}
