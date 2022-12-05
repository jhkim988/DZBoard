package member;

import java.sql.Date;

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
}
