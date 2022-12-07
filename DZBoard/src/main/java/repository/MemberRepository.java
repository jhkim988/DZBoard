package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import member.Member;

public class MemberRepository {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private DataSource dataFactory;
	
	public MemberRepository(DataSource dataFactory) {
		this.dataFactory = dataFactory;
	}
	
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
	
	public Member findOneMemberById(String id) {
		return findOneMember("select * from tb_dzboard_member where id = ?", id);
	}
	
	public Member findOneMemberByEmail(String email) {
		return findOneMember("select * from tb_dzboard_member where email = ?", email);
	}
	
	public Member findOneMemberByPhone(String phone) {
		return findOneMember("select * from tb_dzboard_member where phone = ?", phone);
	}
	
	public List<Member> findMembersAll() {
		return findMembers("select * from tb_dzboard_member order by id limit 10");
	}
	
	public List<Member> findMembersAll(String last) {
		return findMembers("select * from tb_dzboard_member where id > ? order by id limit 10", last);
	}

	public List<Member> findMembersByName(String name) {
		return findMembers("select * from tb_dzboard_member where name = ? order by id limit 10", name);
	}
	public List<Member> findMembersByName(String name, String last) {
		return findMembers("select * from tb_dzboard_member where name = ? and id > ? order by id limit 10", name, last);
	}
	
	public List<Member> findMembersByCreated(Timestamp from, Timestamp to) {
		return findMembersByTimestamp("select * from tb_dzboard_member where createdAt between ? and ? order by createdAt asc, id asc limit 10", from, to);
	}
	
	public List<Member> findMembersByCreated(Timestamp from, Timestamp to, String lastId, Timestamp lastCraetedAt) {
		return findMembersByTimestamp("select * from tb_dzboard_member where (createdAt between ? and ?) and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10", lastId, from, to, lastCraetedAt, lastCraetedAt);
	}
	
	public List<Member> findMembersByUpdated(Timestamp from, Timestamp to) {
		return findMembersByTimestamp("select * from tb_dzboard_member where updatedAt between ? and ? order by updatedAt asc, id asc limit 10", from, to);
	}
	
	public List<Member> findMembersByUpdated(Timestamp from, Timestamp to, String lastId, Timestamp lastUpdatedAt) {
		return findMembersByTimestamp("select * from tb_dzboard_member where (updatedAt between ? and ?) and (updatedAt > ? or (updatedAt = ? and id > ?)) order by updatedAt asc, id asc limit 10", lastId, from, to, lastUpdatedAt, lastUpdatedAt);
	}

	public List<Member> findMembersByAuthority(String level) {
		return findMembers("select * from tb_dzboard_member where authority = ? order by id limit 10", level);
	}
	
	public List<Member> findMembersByAuthority(String level, String last) {
		return findMembers("select * from tb_dzboard_member where authority = ? and id > ? order by id asc limit 10", level, last);
	}
	
	public boolean addMember(Member member) {
		open();
		try {
			pstmt = conn.prepareStatement("insert into tb_dzboard_member (id, pwd, name, email, phone) value (?, ?, ?, ?, ?)");
			pstmt.setString(1, member.getId());
			pstmt.setString(2, member.getPwd());
			pstmt.setString(3, member.getName());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			return pstmt.executeUpdate() == 1;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}
	
	public boolean updateMember(Member member) {
		open();
		try {
			pstmt = conn.prepareStatement("update tb_dzboard_member set pwd = ?, name = ?, email = ?, phone = ?, authority = ? where id = ?");
			pstmt.setString(1, member.getPwd());
			pstmt.setString(2, member.getName());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getPhone());
			pstmt.setInt(5, member.getAuthority());
			pstmt.setString(6, member.getId());
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteMemberById(String id) {
		open();
		try {
			pstmt = conn.prepareStatement("delete from tb_dzboard_member where id = ?");
			pstmt.setString(1, id);
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			
		} finally {
			close();
		}
		return false;
	}
	

	
	public Member findOneMember(String query, String value) {
		open();
		Member ret = null;
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, value);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = resultSetToMember();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}
	
	public List<Member> findMembers(String query, String... value) {
		open();
		List<Member> ret = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(query);
			for (int i = 0; i < value.length; i++) {
				pstmt.setString(i+1, value[i]);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToMember());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}
	
	private Member resultSetToMember() {
		try {
			return Member.builder()
					.id(rs.getString("id"))
					.pwd(rs.getString("pwd"))
					.name(rs.getString("name"))
					.email(rs.getString("email"))
					.phone(rs.getString("phone"))
					.createdAt(rs.getTimestamp("createdAt"))
					.updatedAt(rs.getTimestamp("updatedAt"))
					.authority(rs.getInt("authority"))
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Member> findMembersByTimestamp(String query, Timestamp...timestamps) {
		List<Member> ret = new ArrayList<>();
		open();
		try {
			pstmt = conn.prepareStatement(query);
			for (int i = 1; i <= timestamps.length; i++) {
				pstmt.setTimestamp(i, timestamps[i-1]);
			}
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(Member.builder()
						.id(rs.getString("id"))
						.pwd(rs.getString("pwd"))
						.name(rs.getString("name"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.createdAt(rs.getTimestamp("createdAt"))
						.updatedAt(rs.getTimestamp("updatedAt"))
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
	
	public List<Member> findMembersByTimestamp(String query, String lastId, Timestamp...timestamps) {
		List<Member> ret = new ArrayList<>();
		open();
		try {
			pstmt = conn.prepareStatement(query);
			for (int i = 1; i <= timestamps.length; i++) {
				pstmt.setTimestamp(i, timestamps[i-1]);
			}
			pstmt.setString(timestamps.length+1, lastId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(Member.builder()
						.id(rs.getString("id"))
						.pwd(rs.getString("pwd"))
						.name(rs.getString("name"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.createdAt(rs.getTimestamp("createdAt"))
						.updatedAt(rs.getTimestamp("updatedAt"))
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
}
