package member;

import java.sql.Date;

import org.json.JSONObject;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class Member {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private String phone;
	private Date createdAt;
	private Date updatedAt;
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
}
