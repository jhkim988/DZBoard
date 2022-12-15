package board.postsearch;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.PostRepository;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import board.Post;

@WebServlet("/board/content")
public class PostSearchByContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			return;
		}
		request.setAttribute("posts", posts);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/board/board.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
