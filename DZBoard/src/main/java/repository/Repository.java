package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

public class Repository {
	private static DataSource dataFactory;
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;

	void open() {
		try {
			conn = dataFactory.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void commit() {
		try {
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void rollback() {
		try {
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void setAutoCommit(boolean autoCommit) {
		try {
			conn.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	int executeUpdatePreparedStatement(String query, Object... params) {
		open();
		try {
			pstmt = conn.prepareStatement(query);
			for (int i = 0; i < params.length; i++) {
				pstmt.setObject(i + 1, params[i]);
			}
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return -1;
	}

	ResultSet executeQuery(String query, Object... params) throws SQLException {
		pstmt = conn.prepareStatement(query);
		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
		return rs = pstmt.executeQuery();
	}

	public static void setDataFactory(DataSource dataSource) {
		dataFactory = dataSource;
	}
}
