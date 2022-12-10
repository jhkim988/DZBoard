package board;

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

@WebServlet("/board/deletePost")
public class DeletePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		
		PostRepository postRepository = new PostRepository();

		Post post = postRepository.findOnePostById(id);
		
		// 성공
		if (post != null && post.isSameAuthor(loginMember) && postRepository.deletePost(post)) {
			response.sendRedirect("/DZBoard/board");
			return;
		}
		
		// 실패: 작성자가 아니거나 db 작업 실패
		response.sendRedirect("/DZBoard/board/view?id="+id);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}