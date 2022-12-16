package index;

import java.io.IOException;
import java.util.List;

import board.Post;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.PostRepository;
import server.Action;

public class IndexAction {

	public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostRepository postRepository = new PostRepository();
		List<Post> notices = postRepository.listCategoryHeader("공지");
		List<Post> qnas = postRepository.listCategoryHeader("Q&A");
		List<Post> generals = postRepository.listCategoryHeader("일반");
		request.setAttribute("notices", notices);
		request.setAttribute("qnas", qnas);
		request.setAttribute("generals", generals);
		return "/resources/home.jsp";
	}

}
