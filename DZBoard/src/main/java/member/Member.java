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
		json.put("pwd", id);
		json.put("name", id);
		json.put("email", id);
		json.put("phone", id);
		json.put("createAt", id);
		json.put("updatedAt", id);
		json.put("authority", authority);
		return json;
	}
}
