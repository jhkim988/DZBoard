package entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UrlAuth {
	private String url;
	private int authority;
	private String note;
}
