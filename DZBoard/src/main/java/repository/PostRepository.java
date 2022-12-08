package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
			if (conn != null)
				conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean addPost(Post post, Member member) {
		open();
		try {
			pstmt = conn.prepareStatement("insert into tb_dzboard_board (author, title, content, category) value (?, ?, ?, ?)");
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
				ret.add(Post.builder()
						.id(rs.getInt("id"))
						.author(rs.getString("author"))
						.title(rs.getString("title"))
						.createdAt(rs.getTimestamp("createdAt"))
						.category(rs.getString("category"))
						.viewcount(rs.getInt("viewcount"))
						.good(rs.getInt("good"))
						.bad(rs.getInt("bad"))
						.build());
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
			pstmt = next
						? conn.prepareStatement("select * from tb_dzboard_board where createdAt < ? or (createdAt = ? and id < ?) order by createdAt desc, id desc limit 10")
						: conn.prepareStatement("select * from tb_dzboard_board where createdAt > ? or (createdAt = ? and id > ?) order by createdAt asc, id asc limit 10");
			pstmt.setTimestamp(1, createdAt);
			pstmt.setTimestamp(2, createdAt);
			pstmt.setInt(3, postId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Post post = Post.builder()
						.id(rs.getInt("id"))
						.category(rs.getString("category"))
						.title(rs.getString("title"))
						.author(rs.getString("author"))
						.viewcount(rs.getInt("viewcount"))
						.good(rs.getInt("good"))
						.bad(rs.getInt("bad"))
						.createdAt(rs.getTimestamp("createdAt")).build();
				ret.add(post);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
		if (!next) {
			for (int i = 0; i < ret.size()/2; i++) {
				Post post = ret.get(i);
				ret.set(i, ret.get(ret.size()-i-1));
				ret.set(ret.size()-i-1, post);
			}
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
				return Post.builder()
						.id(rs.getInt("id"))
						.author(rs.getString("author"))
						.title(rs.getString("title"))
						.content(rs.getString("content"))
						.createdAt(rs.getTimestamp("createdAt"))
						.category(rs.getString("category"))
						.viewcount(rs.getInt("viewcount"))
						.good(rs.getInt("good"))
						.bad(rs.getInt("bad"))
						.build();
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
			pstmt = conn.prepareStatement("delete from tb_dzboard_board where id = ?");
			pstmt.setInt(1, post.getId());
			return pstmt.executeUpdate() == 1;
		} catch (Exception e) {
			
		} finally {
			close();
		}
		return false;
	}

	public boolean updatePost(Post oldPost, Post newPost) {
		open();
		try {
			pstmt = conn.prepareStatement("update tb_dzboard_board set title = ?, content = ?, category = ? where id = ?");
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

	public static void setDataFactory(DataSource dataSource) {
		dataFactory = dataSource;
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
}
