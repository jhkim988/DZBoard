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

@WebServlet("/board/createPost")
public class CreatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		if (member == null) {
			return; // 실패
		}
		
		String title = request.getParameter("title");
		String category = request.getParameter("category");
		String content = request.getParameter("content");
		Post post = Post.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();
		
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		PostRepository postRepository = new PostRepository(dataFactory);
		if (postRepository.addPost(post, member)) {
			response.sendRedirect("/DZBoard/board");
			return;
		}
		// 실패
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
