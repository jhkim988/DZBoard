package board;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public final class Category {
	private String value;
	private String textContent;
	private int authority;
}
