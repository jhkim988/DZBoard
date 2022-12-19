package board;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import entities.Post;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.PostRepository;

public class PostSearch {
	
	// TODO: 답변형 기능 추가에 따른 쿼리 수정 필요!
	public String searchAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		String next = request.getParameter("next");
		
		PostRepository postRepository = new PostRepository();
		
		List<Post> posts = null;
		
		if (id == null) {
			posts = postRepository.listPostHeader();
		} else {
			int postId = Integer.parseInt(id);
			Timestamp createdAt = Timestamp.valueOf(date);
			posts = postRepository.listPostHeader(postId, createdAt, Boolean.valueOf(next));
		}
		if (posts.size() == 0) {
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		request.setAttribute("posts", posts);
		return "/resources/board/board.jsp";
	}
	
	public String searchByAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("query");

		String id = request.getParameter("id");
		String date = request.getParameter("date");
		String next = request.getParameter("next");

		PostRepository postRepository = new PostRepository();
		List<Post> posts = null;
		if (id == null) {
			posts = postRepository.listPostHeaderOfAuthor(query);
		} else {
			int postId = Integer.parseInt(id);
			Timestamp createdAt = Timestamp.valueOf(date);
			posts = postRepository.listPostHeaderOfAuthor(query, postId, createdAt, Boolean.valueOf(next));
		}
		if (posts.size() == 0) {
			response.sendRedirect(request.getHeader("referer"));
			return ""; // TODO: Test
		}
		request.setAttribute("posts", posts);
		return "/resources/board/board.jsp";
	}

	public String searchByCategory(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("query");

		String id = request.getParameter("id");
		String date = request.getParameter("date");
		String next = request.getParameter("next");

		PostRepository postRepository = new PostRepository();
		List<Post> posts = null;
		if (id == null) {
			posts = postRepository.listPostHeaderOfCategory(query);
		} else {
			int postId = Integer.parseInt(id);
			Timestamp createdAt = Timestamp.valueOf(date);
			posts = postRepository.listPostHeaderOfCategory(query, postId, createdAt, Boolean.valueOf(next));
		}
		if (posts.size() == 0) {
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		request.setAttribute("posts", posts);
		return "/resources/board/board.jsp";
	}
	
	public String searchByContent(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String query = request.getParameter("query");
		
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		String next = request.getParameter("next");

		PostRepository postRepository = new PostRepository();
		List<Post> posts = null;
		if (id == null) {
			posts = postRepository.listPostHeaderOfContent(query);
		} else {
			int postId = Integer.parseInt(id);
			Timestamp createdAt = Timestamp.valueOf(date);
			posts = postRepository.listPostHeaderOfContent(query, postId, createdAt, Boolean.valueOf(next));
		}
		if (posts.size() == 0) {
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		request.setAttribute("posts", posts);
		return "/resources/board/board.jsp";
	}
	
	public String searchByGood(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		int good = 0;
		try {
			good = Integer.parseInt(query);
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		String next = request.getParameter("next");

		PostRepository postRepository = new PostRepository();
		List<Post> posts = null;
		if (id == null) {
			posts = postRepository.listPostHeaderOfGood(good);
		} else {
			int postId = Integer.parseInt(id);
			Timestamp createdAt = Timestamp.valueOf(date);
			posts = postRepository.listPostHeaderOfGood(good, postId, createdAt, Boolean.valueOf(next));
		}
		if (posts.size() == 0) {
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		request.setAttribute("posts", posts);
		return "/resources/board/board.jsp";
	}
	
	public String searchByPostId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		int postId = 0;
		try {
			postId = Integer.parseInt(query);
		} catch (Exception e) {
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		
		PostRepository postRepository = new PostRepository();
		List<Post> posts = Arrays.asList(postRepository.findOnePostById(postId));
		if (posts.size() == 0) {
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		request.setAttribute("posts", posts);
		return "/resources/board/board.jsp";
	}
	
	public String searchByTitle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String query = request.getParameter("query");
		
		String id = request.getParameter("id");
		String date = request.getParameter("date");
		String next = request.getParameter("next");

		PostRepository postRepository = new PostRepository();
		List<Post> posts = null;
		if (id == null) {
			posts = postRepository.listPostHeaderOfTitle(query);
		} else {
			int postId = Integer.parseInt(id);
			Timestamp createdAt = Timestamp.valueOf(date);
			posts = postRepository.listPostHeaderOfTitle(query, postId, createdAt, Boolean.valueOf(next));
		}
		if (posts.size() == 0) {
			response.sendRedirect(request.getHeader("referer"));
			return "";
		}
		request.setAttribute("posts", posts);
		return "/resources/board/board.jsp";
	}
}
