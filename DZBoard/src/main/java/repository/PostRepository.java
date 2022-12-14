package repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import board.Post;
import member.Member;

public class PostRepository {
	private Repository repository = new Repository();
	private ResultSet rs;

	public List<Post> listCategoryHeader(String category) {
		return findPosts("select * from tb_dzboard_board where category = ? order by createdAt desc, id desc limit 5", category);
	}
	
	public List<Post> listPostHeader() {
		return findPosts("select * from tb_dzboard_board order by createdAt desc, id desc limit 10");
	}

	public List<Post> listPostHeader(int postId, Timestamp createdAt, Boolean next) {
		return paging(next
				, "select * from tb_dzboard_board where createdAt < ? or (createdAt = ? and id < ?) order by createdAt desc, id desc limit 10"
				, "select * from tb_dzboard_board where createdAt > ? or (createdAt = ? and id > ?) order by createdAt asc, id asc limit 10"
				, createdAt, createdAt, postId);
	}

	public List<Post> listPostHeaderOfCategory(String category) {
		return findPosts("select * from tb_dzboard_board where category = ? order by createdAt desc, id desc limit 10"
				, category);
	}

	public List<Post> listPostHeaderOfCategory(String category, int postId, Timestamp createdAt, Boolean next) {
		return paging(next
				, "select * from tb_dzboard_board where category = ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10"
				, "select * from tb_dzboard_board where category = ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10"
				, category, createdAt, createdAt, postId);
	}

	public List<Post> listPostHeaderOfTitle(String title) {
		return findPosts("select * from tb_dzboard_board where title like ? order by createdAt desc, id desc limit 10"
				, surroundLikeWildCard(title));
	}

	public List<Post> listPostHeaderOfTitle(String title, int postId, Timestamp createdAt, Boolean next) {
		return paging(next
				, "select * from tb_dzboard_board where title like ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10"
				, "select * from tb_dzboard_board where title like ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10"
				, surroundLikeWildCard(title), createdAt, createdAt, postId);
	}

	public List<Post> listPostHeaderOfAuthor(String author) {
		return findPosts("select * from tb_dzboard_board where author like ? order by createdAt desc, id desc limit 10"
				, surroundLikeWildCard(author));
	}

	public List<Post> listPostHeaderOfAuthor(String author, int postId, Timestamp createdAt, Boolean next) {
		return paging(next
				, "select * from tb_dzboard_board where author like ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10"
				, "select * from tb_dzboard_board where author like ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10"
				, surroundLikeWildCard(author), createdAt, createdAt, postId);
	}

	public List<Post> listPostHeaderOfContent(String content) {
		return findPosts("select * from tb_dzboard_board where content like ? order by createdAt desc, id desc limit 10"
				, surroundLikeWildCard(content));
	}

	public List<Post> listPostHeaderOfContent(String content, int postId, Timestamp createdAt, Boolean next) {
		return paging(next
				, "select * from tb_dzboard_board where content like ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10"
				, "select * from tb_dzboard_board where content like ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10"
				, surroundLikeWildCard(content), createdAt, createdAt, postId);
	}

	public List<Post> listPostHeaderOfGood(int good) {
		return findPosts(
				"select * from tb_dzboard_board where good >= ? order by createdAt desc, id desc limit 10"
				, good);
	}

	public List<Post> listPostHeaderOfGood(int good, int postId, Timestamp createdAt, Boolean next) {
		return paging(next
				, "select * from tb_dzboard_board where good >= ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10"
				, "select * from tb_dzboard_board where good >= ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10"
				, good, createdAt, createdAt, postId);
	}
	
	public List<Post> findPosts(String query, Object... params) {
		List<Post> ret = new ArrayList<>();
		repository.open();
		try {
			rs = repository.executeQuery(query, params);
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return ret;
	}
	
	public List<Post> paging(boolean next, String nextQuery, String prevQuery, Object... params) {
		List<Post> ret = next ? findPosts(nextQuery, params) : findPosts(prevQuery, params);
		if (!next) {
			Collections.reverse(ret);
		}
		return ret;
	}
	
	public Post findOnePostById(int postId) {
		return findOnePost("select * from tb_dzboard_board where id = ?", postId);
	}

	public Post findOnePost(String query, Object... params) {
		repository.open();
		try {
			rs = repository.executeQuery(query, params);
			if (rs.next()) {
				return resultSetToHeader();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return null;
	}
	
	public int createPost(Post post, Member member) {
		repository.open();
		repository.setAutoCommit(false);
		try {
			int executeUpdate = repository.executeUpdate(
					"insert tb_dzboard_board (author, title, content, category) value (?, ?, ?, ?)"
					, member.getId(), post.getTitle(), post.getContent(), post.getCategory());
			if (executeUpdate != 1) {
				repository.rollback();
				return -1;
			}
			rs = repository.executeQuery("select last_insert_id() from tb_dzboard_board");
			if (rs.next()) {
				return rs.getInt(1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.commit();
			repository.close();
		}
/*
		return repository.executeUpdatePreparedStatement(
				"insert into tb_dzboard_board (author, title, content, category) value (?, ?, ?, ?)"
				, member.getId(), post.getTitle(), post.getContent(), post.getCategory()) == 1;
*/
		return -1;
	}
	
	public boolean createTestPost(Post post, Member member) {
		return repository.executeUpdatePreparedStatement(
				"insert into tb_dzboard_board (author, title, content, category, good) values (?, ?, ?, ?, ?)"
				, member.getId(), post.getTitle(), post.getContent(), post.getCategory(), post.getGood()) == 1;
	}
	
	public boolean deletePost(Post post) {
		repository.open();
		try {
			CallableStatement cstmt = repository.prepareCall("{call pc_dzboard_deletePost(?, ?)}");
			cstmt.setInt(1, post.getId());
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.execute();
			return cstmt.getInt(2) == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return false;
	}

	public boolean updatePost(Post oldPost, Post newPost) {
		return repository.executeUpdatePreparedStatement(
				"update tb_dzboard_board set title = ?, content = ?, category = ? where id = ?"
				, newPost.getTitle()
				, newPost.getContent()
				, newPost.getCategory()
				, oldPost.getId()) == 1;
	}

	public boolean incrementViewCount(int postId) {
		return repository.executeUpdatePreparedStatement(
				"update tb_dzboard_board set viewcount = viewcount+1 where id = ?"
				, postId) == 1;
	}

	public boolean incrementGood(int postId) {
		return repository.executeUpdatePreparedStatement(
				"update tb_dzboard_board set good = good+1 where id = ?"
				, postId) == 1;
	}

	public boolean incrementBad(int postId) {
		return repository.executeUpdatePreparedStatement(
				"update tb_dzboard_board set bad = bad+1 where id = ?"
				, postId) == 1;
	}

	private Post resultSetToHeader() {
		try {
			Post ret = Post.builder().id(rs.getInt("id")).author(rs.getString("author")).title(rs.getString("title"))
					.content(rs.getString("content")).createdAt(rs.getTimestamp("createdAt"))
					.category(rs.getString("category")).viewcount(rs.getInt("viewcount")).good(rs.getInt("good"))
					.bad(rs.getInt("bad")).build();
			if (ret != null)
				return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("SQL Result Set Exception");
	}

	private String surroundLikeWildCard(String str) {
		return new StringBuilder("%").append(str).append("%").toString();
	}
}