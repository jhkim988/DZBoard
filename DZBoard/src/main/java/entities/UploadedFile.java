package entities;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadedFile {
	private int f_id;
	private int post_id;
	private String org_name;
	private String real_name;
	private String content_type;
	private long content_length;
	private Timestamp createdAt;
}
