package board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.Member;
import repository.CategoryRepository;
import repository.PostRepository;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

@WebServlet("/board/updateForm")
public class UpdateFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		int postId = Integer.parseInt(request.getParameter("id"));
		
		request.setAttribute("categoryList", getCategoryList(loginMember.getAuthority()));
		
		PostRepository postRepository = new PostRepository();
		Post oldPost = postRepository.findOnePostById(postId);
		if (oldPost.isSameAuthor(loginMember)) {
			request.setAttribute("oldPost", oldPost);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/board/update.jsp");
			dispatcher.forward(request, response);
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private List<Category> getCategoryList(int authority) {
		@SuppressWarnings("unchecked")
		List<Category> allCategories = (List<Category>) getServletContext().getAttribute("allCategoryList");
		return allCategories.stream().filter(category -> category.getAuthority() <= authority).collect(Collectors.toList());
	}

}
