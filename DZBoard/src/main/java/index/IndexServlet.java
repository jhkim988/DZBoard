package index;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.PostRepository;

import java.io.IOException;
import java.util.List;

import board.Post;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostRepository postRepository = new PostRepository();
		List<Post> notices = postRepository.listCategoryHeader("공지");
		List<Post> qnas = postRepository.listCategoryHeader("Q&A");
		List<Post> generals = postRepository.listCategoryHeader("일반");
		request.setAttribute("notices", notices);
		request.setAttribute("qnas", qnas);
		request.setAttribute("generals", generals);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
