package board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.Member;
import repository.PostRepository;

import java.io.IOException;

import javax.sql.DataSource;

@WebServlet("/board/updateForm")
public class UpdateFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		if (loginMember == null) {
			response.sendRedirect("/DZBoard/member/login.html");
			return;
		}
		
		int postId = Integer.parseInt(request.getParameter("id"));
		
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		PostRepository postRepository = new PostRepository(dataFactory);
		Post oldPost = postRepository.findOnePostById(postId);
		if (oldPost.isSameAuthor(loginMember)) {
			request.setAttribute("id", postId);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/board/update.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
