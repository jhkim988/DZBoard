package repository;

import java.lang.reflect.Proxy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.Member;

public class MemberRepository extends Repository {
	
	public Boolean addMember(Member member) throws SQLException {
		pstmt = conn.prepareStatement("insert into tb_dzboard_member (id, pwd, name, email, phone) value (?, ?, ?, ?, ?)");
		pstmt.setString(1, member.getId());
		pstmt.setString(2, member.getPwd());
		pstmt.setString(3, member.getName());
		pstmt.setString(4, member.getEmail());
		pstmt.setString(5, member.getPhone());
		return pstmt.executeUpdate() == 1;
	}
	
	public List<Member> listMembers() throws SQLException {
		List<Member> ret = new ArrayList<>();
		pstmt = conn.prepareStatement("select * from member");
		rs = pstmt.executeQuery();
		while (rs.next()) {
			ret.add(Member.builder()
					.id(rs.getString("id"))
					.pwd(rs.getString("pwd"))
					.name(rs.getString("name"))
					.email(rs.getString("email"))
					.phone(rs.getString("phone"))
					.createdAt(rs.getDate("createdAt"))
					.updatedAt(rs.getDate("updatedAt"))
					.build());
		}
		return ret;
	}
	
	public Member findUserById(String id) {
		open();
		Member ret = null;
		try {
			pstmt = conn.prepareStatement("select * from tb_dzboard_member where id = ?");
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				ret = Member.builder()
						.id(rs.getString("id"))
						.pwd(rs.getString("pwd"))
						.name(rs.getString("name"))
						.email(rs.getString("email"))
						.phone(rs.getString("phone"))
						.createdAt(rs.getDate("createdAt"))
						.updatedAt(rs.getDate("updatedAt"))
						.build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}
	
	public static MemberRepository getProxy() {
		MemberRepository repository = new MemberRepository();
		return (MemberRepository) Proxy.newProxyInstance(
				repository.getClass().getClassLoader()
				, repository.getClass().getInterfaces()
				, new MemberRepositoryHandler(repository));
	}
}
