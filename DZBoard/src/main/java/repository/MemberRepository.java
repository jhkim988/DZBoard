package repository;

import java.util.ArrayList;
import java.util.List;

import member.Member;

public class MemberRepository extends Repository {
	
	public boolean addMember(Member member) {
		open();
		try {
			pstmt = conn.prepareStatement("insert into tb_dzboard_member (id, pwd, name, email, phone, authority) value (?, ?, ?, ?, ?, ?)");
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
	
	public List<Member> findMembers() {
		return findMembers("select * from tb_dzboard_member");
	}
	
	public Member findOneMemberById(String id) {
		return findOneMember("select * from tb_dzboard_member where id = ?", id);
	}
	
	public List<Member> findMembersByName(String name) {
		return findMembers("select * from tb_dzboard_member where name = ?", name);
	}
	
	public Member findOneMemberByEmail(String email) {
		return findOneMember("select * from tb_dzboard_member where email = ?", email);
	}
	
	public Member findOneMemberByPhone(String phone) {
		return findOneMember("select * from tb_dzboard_member where phone = ?", phone);
	}
	
	public List<Member> findMembersByCreated(String from, String to) {
		return findMembers("select * from tb_dzboard_member where createdAt between ? and ?", from, to);
	}
	
	public List<Member> findMembersByUpdated(String from, String to) {
		return findMembers("select * from tb_dzboard_member where updatedAt between ? and ?", from, to);
	}
	
	public List<Member> findMembersByAuthority(String level) {
		return findMembers("select * from tb_dzboard_member where authority = ?", level);
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
	
	public Member resultSetToMember() {
		try {
			return Member.builder()
					.id(rs.getString("id"))
					.pwd(rs.getString("pwd"))
					.name(rs.getString("name"))
					.email(rs.getString("email"))
					.phone(rs.getString("phone"))
					.createdAt(rs.getDate("createdAt"))
					.updatedAt(rs.getDate("updatedAt"))
					.authority(rs.getInt("authority"))
					.build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
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
}
