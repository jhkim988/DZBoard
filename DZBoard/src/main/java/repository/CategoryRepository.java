package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import board.Category;

public class CategoryRepository {
	private static DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private void open() {
		try {
			conn = dataFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<Category> findAllCategories() {
		List<Category> ret = new ArrayList<>();
		open();
		try {
			pstmt = conn.prepareStatement("select * from tb_dzboard_board_category");
			rs = pstmt.executeQuery();
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
			close();
		}
		return ret;
	}
	
	public static void setDataFactory(DataSource dataSource) {
		dataFactory = dataSource;
	}
}
