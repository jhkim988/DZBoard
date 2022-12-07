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

@WebServlet("/board/updatePost")
public class UpdatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("HERE: UpdatePostServlet");
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		int postId = Integer.parseInt(request.getParameter("id"));
		if (loginMember == null) {
			response.sendRedirect("/DZBoard/board/view?id="+postId);
			return;
		}
	
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		PostRepository postRepository = new PostRepository(dataFactory);
		Post oldPost = postRepository.findOnePostById(postId);
		
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String category = request.getParameter("category");
		Post newPost = Post.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();
		if (oldPost != null && oldPost.isSameAuthor(loginMember) && postRepository.updatePost(oldPost, newPost)) {
			response.sendRedirect("/DZBoard/board/view?id="+postId);
			return;
		}
		 response.sendRedirect("/DZBoard/board/updatePost?id="+postId);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
