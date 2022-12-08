package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class GoodBadRepository {
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
	
	public boolean click(String isGood, String memberId, int postId) {
		open();
		try {
			pstmt = conn.prepareStatement("insert into tb_dzboard_goodbad (good, memberid, postid) values (?, ?, ?)");
			pstmt.setString(1, isGood);
			pstmt.setString(2, memberId);
			pstmt.setInt(3, postId);
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			
		} finally {
			close();
		}
		return false;
	}
	
	public static void setDataFactory(DataSource dataSource) {
		dataFactory = dataSource;
	}
}
