package entities;

import java.sql.Timestamp;

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
}
