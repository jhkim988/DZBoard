package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import admin.UrlAuth;

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
	
	public Map<String, UrlAuth> findAllUrlAuth() {
		Map<String, UrlAuth> ret = new HashMap<>();
		open();
		try {
			pstmt = conn.prepareStatement("select * from tb_dzboard_urlauth");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String url = rs.getString("url");
				int authority = rs.getInt("authority");
				String note = rs.getString("note");
				ret.put(url, UrlAuth.builder()
							.url(url)
							.authority(authority)
							.note(note)
							.build());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}
	
	public boolean createUrlAuth(UrlAuth urlAuth) {
		open();
		try {
			pstmt = conn.prepareStatement("insert into tb_dzboard_")
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}
	
	public boolean deleteUrlAuthByUrl(String url) {
		open();
		try {
			pstmt = conn.prepareStatement("delete from tb_dzboard_urlauth where url = ?");
			pstmt.setString(1, url);
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
 	}
	
	public static void setDataFactory(DataSource dataSource) {
		dataFactory = dataSource;
	}
}
