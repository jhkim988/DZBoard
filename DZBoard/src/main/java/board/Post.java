package board;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import member.Member;

@Data
@Builder
public class Post {
	private int id;
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
