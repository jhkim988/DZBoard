package repository;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import member.Member;

public class MemberRepository {
	private Repository repository = new Repository();
	private ResultSet rs;
	
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
		return findMembers(
				"select * from tb_dzboard_member where name = ? order by id limit 10"
				, name);
	}
	
	public List<Member> findMembersByName(String name, String last) {
		return findMembers(
				"select * from tb_dzboard_member where name = ? and id > ? order by id limit 10"
				, name, last);
	}
	
	public List<Member> findMembersByCreated(Timestamp from, Timestamp to) {
		return findMembers(
				"select * from tb_dzboard_member where createdAt between ? and ? order by createdAt desc, id asc limit 10"
				, from, to);
	}
	
	public List<Member> findMembersByCreated(Timestamp from, Timestamp to, String lastId, Timestamp lastCraetedAt) {
		return findMembers(
				"select * from tb_dzboard_member where (createdAt between ? and ?) and (createdAt < ? or (createdAt = ? and id > ?)) order by createdAt desc, id asc limit 10"
				, from, to, lastCraetedAt, lastCraetedAt, lastId);
	}
	
	public List<Member> findMembersByUpdated(Timestamp from, Timestamp to) {
		return findMembers(
				"select * from tb_dzboard_member where updatedAt between ? and ? order by updatedAt desc, id asc limit 10"
				, from, to);
	}
	
	public List<Member> findMembersByUpdated(Timestamp from, Timestamp to, String lastId, Timestamp lastUpdatedAt) {
		return findMembers(
				"select * from tb_dzboard_member where (updatedAt between ? and ?) and (updatedAt < ? or (updatedAt = ? and id > ?)) order by updatedAt desc, id asc limit 10"
				, from, to, lastUpdatedAt, lastUpdatedAt, lastId);
	}

	public List<Member> findMembersByAuthority(String level) {
		return findMembers("select * from tb_dzboard_member where authority = ? order by id limit 10", level);
	}
	
	public List<Member> findMembersByAuthority(String level, String last) {
		return findMembers("select * from tb_dzboard_member where authority = ? and id > ? order by id asc limit 10", level, last);
	}
	
	public boolean createMember(Member member) {
		return repository.executeUpdatePreparedStatement(
				"insert into tb_dzboard_member (id, pwd, name, email, phone) value (?, ?, ?, ?, ?)"
				, member.getId()
				, member.getPwd()
				, member.getName()
				, member.getEmail()
				, member.getPhone()) == 1;
	}
	
	public boolean updateMember(Member member) {
		return repository.executeUpdatePreparedStatement(
				"update tb_dzboard_member set pwd = ?, name = ?, email = ?, phone = ?, authority = ? where id = ?"
				, member.getPwd()
				, member.getName()
				, member.getEmail()
				, member.getPhone()
				, member.getAuthority()
				, member.getId()) == 1;
	}
	
	public boolean updateUpdatedAt(Member member) {
		return repository.executeUpdatePreparedStatement(
				"update tb_dzboard_member set updatedAt = ? where id = ?"
				, Timestamp.valueOf(LocalDateTime.now())
				, member.getId()) == 1;
	}
	
	// TODO: Change Procedure
	public boolean deleteMemberById(String id) {
		Member member = findOneMemberById(id);
		repository.open();
		try {
			repository.setAutoCommit(false);
			int deleteCount = repository.executeUpdatePreparedStatement(
					"delete from tb_dzboard_member where id = ?"
					, id);
			int moveCount = repository.executeUpdatePreparedStatement(
					"insert into tb_dzboard_tmp_member (id, pwd, name, email, phone, createdAt, authority) values (?, ?, ?, ?, ?, ?, ?)"
					, member.getId()
					, member.getPwd()
					, member.getName()
					, member.getEmail()
					, member.getPhone()
					, member.getCreatedAt()
					, member.getAuthority());
			if (deleteCount != moveCount) {
				repository.rollback();
				return false;
			}
			repository.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return false;
	}
	
	public Member findOneMember(String query, Object... params) {
		repository.open();
		Member ret = null;
		try {
			rs = repository.executeQuery(query,  params);
			if (rs.next()) {
				ret = resultSetToMember();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return ret;
	}
	
	public List<Member> findMembers(String query, Object... params) {
		List<Member> ret = new ArrayList<>();
		repository.open();
		try {
			rs = repository.executeQuery(query, params);
			while (rs.next()) {
				ret.add(resultSetToMember());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
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
		throw new RuntimeException("Fail: ResultSet -> Member");
	}
}
