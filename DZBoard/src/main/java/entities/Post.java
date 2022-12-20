package entities;

import java.sql.Timestamp;

import org.json.JSONObject;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Post {
	private int id;
	private int parent;
	private String author;
	private String title;
	private String content;
	private Timestamp createdAt;
	private String category;
	private int viewcount;
	private int good;
	private int bad;
	
	public boolean isSameAuthor(Member loginMember) {
		return author.equals(loginMember.getId());
	}
	
	public JSONObject toJSON() {
		JSONObject ret = new JSONObject();
		ret.put("id", id);
		ret.put("parent", parent);
		ret.put("author", author);
		ret.put("title", title);
		ret.put("content", content);
		ret.put("createdAt", createdAt);
		ret.put("category", category);
		ret.put("viewcount", viewcount);
		ret.put("good", good);
		ret.put("bad", bad);
		return ret;
	}
	
	public JSONObject headertoJSON() {
		JSONObject ret = new JSONObject();
		ret.put("id", id);
		ret.put("parent", parent);
		ret.put("author", author);
		ret.put("title", title);
		ret.put("createdAt", createdAt);
		ret.put("category", category);
		ret.put("viewcount", viewcount);
		ret.put("good", good);
		ret.put("bad", bad);
		return ret;
	}
}
