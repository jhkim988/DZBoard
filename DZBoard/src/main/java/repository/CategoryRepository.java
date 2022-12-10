package repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import board.Category;

public class CategoryRepository {
	private Repository repository = new Repository();
	private ResultSet rs;
	
	public List<Category> findAllCategories() {
		List<Category> ret = new ArrayList<>();
		repository.open();
		try {
			rs = repository.executeQuery("select * from tb_dzboard_board_category");
			while (rs.next()) {
				ret.add(Category.builder()
						.value(rs.getString("value"))
						.textContent(rs.getString("textContent"))
						.authority(rs.getInt("authority"))
						.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return ret;
	}
}
