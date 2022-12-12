package repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public class Repository {
	private static DataSource dataFactory;
	private static Map<String, String> sqls = Collections.synchronizedMap(new HashMap<>());

	private Connection conn;
	private PreparedStatement pstmt;
	private CallableStatement cstmt;
	private ResultSet rs;

	
	public static String put(String name, String sql) {
		return sqls.put(name, sql);
	}
	public static String getSQL(String name) {
		return sqls.get(name);
	}
	
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
			if (cstmt != null) {
				cstmt.close();
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

	CallableStatement prepareCall(String query) throws SQLException {
		return cstmt = conn.prepareCall(query);
	}
	
	public static void setDataFactory(DataSource dataSource) {
		dataFactory = dataSource;
	}
}
