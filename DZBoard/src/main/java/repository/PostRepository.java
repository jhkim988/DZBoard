package repository;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Member;
import entities.Post;

public class PostRepository {
	private Repository repository = new Repository();
	private ResultSet rs;

	public List<Post> listCategoryHeader(String category) {
		return findPosts("select * from tb_dzboard_board where category = ? order by createdAt desc, id desc limit 5", category);
	}
	
	public List<Post> listPostHeader(int pageNo, int pageSize) {
		return findPosts("select * from tb_dzboard_board as fullsearch join (select id from tb_dzboard_board order by parent desc, id asc limit ?, ?) as covering on fullsearch.id = covering.id"
				, pageNo, pageSize);
// 성능 비교!		
//		return findPosts("select * from tb_dzboard_board order by parent desc, id asc limit ?, ?", pageNo, pageSize);
	}

	public int countPost() {
		return count("select count(*) from tb_dzboard_board");
	}
	
	public List<Post> listPostHeaderOfCategory(int pageNo, int pageSize, String category) {
		return findPosts("select * from tb_dzboard_board as fullsearch join (select id from tb_dzboard_board where category = ? order by parent desc, id asc limit ?, ?) as covering on fullsearch.id = covering.id"
				, category, pageNo, pageSize);
	}
	
	public int countPostByCategory(String category) {
		return count("select count(*) from tb_dzboard_board where category = ?", category);
	}

	public List<Post> listPostHeaderOfTitle(int pageNo, int pageSize, String title) {
		return findPosts("select * from tb_dzboard_board as fullsearch join (select id from tb_dzboard_board where title like concat('%', ?, '%') order by parent desc, id asc limit ?, ?) as covering on fullsearch.id = covering.id"
				, title, pageNo, pageSize);
	}
	
	public int countPostByTitle(String title) {
		return count("select count(*) from tb_dzboard_board where title like concat('%', ?, '%')", title);
	}
	
	public List<Post> listPostHeaderOfContent(int pageNo, int pageSize, String content) {
		return findPosts("select * from tb_dzboard_board where content like concat('%', ?, '%') limit ?, ?"
				, content, pageNo, pageSize);
	}

	public int countPostByContent(String content) {
		return count("select count(*) from tb_dzboard_board where content like concat('%', ?, '%') order by parent desc, id asc", content);
	}
	
	public List<Post> listPostHeaderOfAuthor(int pageNo, int pageSize, String author) {
		return findPosts("select * from tb_dzboard_board as fullsearch join (select id from tb_dzboard_board where author = ? order by parent desc, id asc limit ?, ?) as covering on fullsearch.id = covering.id"
				, author, pageNo, pageSize);
	}

	public int countPostByAuthor(String author) {
		return count("select count(*) from tb_dzboard_board where author = ?", author);
	}
	
	public List<Post> listPostHeaderOfGood(int pageNo, int pageSize, int good) {
		return findPosts(
				"select * from tb_dzboard_board as fullsearch join (select id from tb_dzboard_board where good >= ? order by parent desc, id asc desc limit ?, ?) as covering on fullsearch.id = covering.id"
				, good, pageNo, pageSize);
	}

	public int countPostByGood(int good) {
		return count("select count(*) from tb_dzboard_board where good >= ?", good);
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
		try {
			CallableStatement cstmt = repository.prepareCall("{call pc_dzboard_insertPost(?, ?, ?, ?, ?)}");
			cstmt.setString(1, member.getId());
			cstmt.setString(2,  post.getTitle());
			cstmt.setString(3,  post.getContent());
			cstmt.setString(4,  post.getCategory());
			cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
			cstmt.execute();
			int ret = cstmt.getInt(5);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return -1;
	}
	
	public int createReplyPost(Post post, Member member) {
		repository.open();
		try {
			CallableStatement cstmt = repository.prepareCall("{call pc_dzboard_insertReplyPost(?, ?, ?, ?, ?, ?)}");
			cstmt.setInt(1, post.getParent());
			cstmt.setString(2, member.getId());
			cstmt.setString(3,  post.getTitle());
			cstmt.setString(4,  post.getContent());
			cstmt.setString(5,  post.getCategory());
			cstmt.registerOutParameter(6, java.sql.Types.INTEGER);
			cstmt.execute();
			int ret = cstmt.getInt(6);
			return ret;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return -1;
	}
	
	public boolean createTestPost(Post post, Member member) {
		return repository.executeUpdatePreparedStatement(
				"insert into tb_dzboard_board (parent, author, title, content, category, good) values (?, ?, ?, ?, ?, ?)"
				, post.getParent(), member.getId(), post.getTitle(), post.getContent(), post.getCategory(), post.getGood()) == 1;
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
			Post ret = Post.builder()
					.id(rs.getInt("id"))
					.parent(rs.getInt("parent"))
					.author(rs.getString("author"))
					.title(rs.getString("title"))
					.content(rs.getString("content"))
					.createdAt(rs.getTimestamp("createdAt"))
					.category(rs.getString("category"))
					.viewcount(rs.getInt("viewcount"))
					.good(rs.getInt("good"))
					.bad(rs.getInt("bad")).build();
			if (ret != null)
				return ret;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("SQL Result Set Exception");
	}
	
	private int count(String query, Object...params) {
		repository.open();
		try {
			ResultSet rs = repository.executeQuery(query, params);
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			repository.close();
		}
		return -1;
	}
}