package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

public class UrlAuthRepository {
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
	
	public Map<String, Integer> findAllUrlAuth() {
		Map<String, Integer> ret = new HashMap<>();
		open();
		try {
			pstmt = conn.prepareStatement("select * from tb_dzboard_urlauth");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String url = rs.getString("url");
				int authority = rs.getInt("authority");
				ret.put(url, authority);
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
