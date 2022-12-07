package board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.PostRepository;

import java.io.IOException;

import javax.sql.DataSource;

@WebServlet("/board/view")
public class ViewPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int postId = Integer.parseInt(request.getParameter("id"));
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		PostRepository postRepository = new PostRepository(dataFactory);
		Post post = postRepository.findOnePostById(postId);
		if (post == null) {
			response.sendRedirect("/board");
			return;
		}
		request.setAttribute("post", post);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/view.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
