package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import board.Post;
import member.Member;

public class PostRepository {
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private static DataSource dataFactory;

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
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addPost(Post post, Member member) {
		open();
		try {
			pstmt = conn.prepareStatement(
					"insert into tb_dzboard_board (author, title, content, category) value (?, ?, ?, ?)");
			pstmt.setString(1, member.getId());
			pstmt.setString(2, post.getTitle());
			pstmt.setString(3, post.getContent());
			pstmt.setString(4, post.getCategory());
			
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public List<Post> listPostHeader() {
		open();
		List<Post> ret = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement("select * from tb_dzboard_board order by createdAt desc, id desc limit 10");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}

	public List<Post> listPostHeader(int postId, Timestamp createdAt, Boolean next) {
		List<Post> ret = new ArrayList<>();
		open();
		try {
			pstmt = next ? conn.prepareStatement(
					"select * from tb_dzboard_board where createdAt < ? or (createdAt = ? and id < ?) order by createdAt desc, id desc limit 10")
					: conn.prepareStatement(
							"select * from tb_dzboard_board where createdAt > ? or (createdAt = ? and id > ?) order by createdAt asc, id asc limit 10");
			pstmt.setTimestamp(1, createdAt);
			pstmt.setTimestamp(2, createdAt);
			pstmt.setInt(3, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (!next) {
			Collections.reverse(ret);
		}
		return ret;
	}

	public List<Post> listPostHeaderOfCategory(String category) {
		open();
		List<Post> ret = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(
					"select * from tb_dzboard_board where category = ? order by createdAt desc, id desc limit 10");
			pstmt.setString(1, category);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}

	public List<Post> listPostHeaderOfCategory(int postId, Timestamp createdAt, Boolean next, String category) {
		List<Post> ret = new ArrayList<>();
		open();
		try {
			pstmt = next ? conn.prepareStatement(
					"select * from tb_dzboard_board where category = ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10")
					: conn.prepareStatement(
							"select * from tb_dzboard_board where category = ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10");
			pstmt.setString(1, category);
			pstmt.setTimestamp(2, createdAt);
			pstmt.setTimestamp(3, createdAt);
			pstmt.setInt(4, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (!next) {
			Collections.reverse(ret);
		}
		return ret;
	}

	public List<Post> listPostHeaderOfTitle(String title) {
		open();
		List<Post> ret = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(
					"select * from tb_dzboard_board where title like ? order by createdAt desc, id desc limit 10");
			pstmt.setString(1, surroundLikeWildCard(title));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}

	public List<Post> listPostHeaderOfTitle(int postId, Timestamp createdAt, Boolean next, String title) {
		List<Post> ret = new ArrayList<>();
		open();
		try {
			pstmt = next ? conn.prepareStatement(
					"select * from tb_dzboard_board where title like ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10")
					: conn.prepareStatement(
							"select * from tb_dzboard_board where title like ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10");
			pstmt.setString(1, surroundLikeWildCard(title));
			pstmt.setTimestamp(2, createdAt);
			pstmt.setTimestamp(3, createdAt);
			pstmt.setInt(4, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (!next) {
			Collections.reverse(ret);
		}
		return ret;
	}
	
	public List<Post> listPostHeaderOfAuthor(String author) {
		open();
		List<Post> ret = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(
					"select * from tb_dzboard_board where author like ? order by createdAt desc, id desc limit 10");
			pstmt.setString(1, surroundLikeWildCard(author));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}

	public List<Post> listPostHeaderOfAuthor(int postId, Timestamp createdAt, Boolean next, String author) {
		List<Post> ret = new ArrayList<>();
		open();
		try {
			pstmt = next ? conn.prepareStatement(
					"select * from tb_dzboard_board where author like ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10")
					: conn.prepareStatement(
							"select * from tb_dzboard_board where author like ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10");
			pstmt.setString(1, surroundLikeWildCard(author));
			pstmt.setTimestamp(2, createdAt);
			pstmt.setTimestamp(3, createdAt);
			pstmt.setInt(4, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (!next) {
			Collections.reverse(ret);
		}
		return ret;
	}
	
	public List<Post> listPostHeaderOfContent(String content) {
		open();
		List<Post> ret = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(
					"select * from tb_dzboard_board where content like ? order by createdAt desc, id desc limit 10");
			pstmt.setString(1, surroundLikeWildCard(content));
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}

	public List<Post> listPostHeaderOfContent(int postId, Timestamp createdAt, Boolean next, String content) {
		List<Post> ret = new ArrayList<>();
		open();
		try {
			pstmt = next ? conn.prepareStatement(
					"select * from tb_dzboard_board where content like ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10")
					: conn.prepareStatement(
							"select * from tb_dzboard_board where content like ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10");
			pstmt.setString(1, surroundLikeWildCard(content));
			pstmt.setTimestamp(2, createdAt);
			pstmt.setTimestamp(3, createdAt);
			pstmt.setInt(4, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (!next) {
			Collections.reverse(ret);
		}
		return ret;
	}
	
	public List<Post> listPostHeaderOfGood(int good) {
		open();
		List<Post> ret = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(
					"select * from tb_dzboard_board where good >= ? order by createdAt desc, id desc limit 10");
			pstmt.setInt(1, good);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return ret;
	}

	public List<Post> listPostHeaderOfGood(int postId, Timestamp createdAt, Boolean next, int good) {
		List<Post> ret = new ArrayList<>();
		open();
		try {
			pstmt = next ? conn.prepareStatement(
					"select * from tb_dzboard_board where good >= ? and (createdAt < ? or (createdAt = ? and id < ?)) order by createdAt desc, id desc limit 10")
					: conn.prepareStatement(
							"select * from tb_dzboard_board where good >= ? and (createdAt > ? or (createdAt = ? and id > ?)) order by createdAt asc, id asc limit 10");
			pstmt.setInt(1, good);
			pstmt.setTimestamp(2, createdAt);
			pstmt.setTimestamp(3, createdAt);
			pstmt.setInt(4, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				ret.add(resultSetToHeader());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (!next) {
			Collections.reverse(ret);
		}
		return ret;
	}
	
	public Post findOnePostById(int postId) {
		open();
		try {
			pstmt = conn.prepareStatement("select * from tb_dzboard_board where id = ?");
			pstmt.setInt(1, postId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				return Post.builder().id(rs.getInt("id")).author(rs.getString("author")).title(rs.getString("title"))
						.content(rs.getString("content")).createdAt(rs.getTimestamp("createdAt"))
						.category(rs.getString("category")).viewcount(rs.getInt("viewcount")).good(rs.getInt("good"))
						.bad(rs.getInt("bad")).build();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return null;
	}

	public boolean deletePost(Post post) {
		open();
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement("delete from tb_dzboard_board where id = ?");
			pstmt.setInt(1, post.getId());
			int deleteCount = pstmt.executeUpdate();

			pstmt = conn.prepareStatement(
					"insert into tb_dzboard_tmp_board (id, author, title, content, createdAt, category, viewcount, good, bad) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, post.getId());
			pstmt.setString(2, post.getAuthor());
			pstmt.setString(3, post.getTitle());
			pstmt.setString(4, post.getContent());
			pstmt.setTimestamp(5, post.getCreatedAt());
			pstmt.setString(6, post.getCategory());
			pstmt.setInt(7, post.getViewcount());
			pstmt.setInt(8, post.getGood());
			pstmt.setInt(9, post.getBad());
			int moveCount = pstmt.executeUpdate();
			if (deleteCount != moveCount) {
				conn.rollback();
				return false;
			}
			conn.commit();
			return true;
		} catch (Exception e) {

		} finally {
			close();
		}
		return false;
	}

	public boolean updatePost(Post oldPost, Post newPost) {
		open();
		try {
			pstmt = conn
					.prepareStatement("update tb_dzboard_board set title = ?, content = ?, category = ? where id = ?");
			pstmt.setString(1, newPost.getTitle());
			pstmt.setString(2, newPost.getContent());
			pstmt.setString(3, newPost.getCategory());
			pstmt.setInt(4, oldPost.getId());
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean incrementViewCount(int postId) {
		open();
		try {
			pstmt = conn.prepareStatement("update tb_dzboard_board set viewcount = viewcount+1 where id = ?");
			pstmt.setInt(1, postId);
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean incrementGood(int postId) {
		open();
		try {
			pstmt = conn.prepareStatement("update tb_dzboard_board set good = good+1 where id = ?");
			pstmt.setInt(1, postId);
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	public boolean incrementBad(int postId) {
		open();
		try {
			pstmt = conn.prepareStatement("update tb_dzboard_board set bad = bad+1 where id = ?");
			pstmt.setInt(1, postId);
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
