package board;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/board/create")
public class CreatePostFormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Member member = (Member) request.getSession().getAttribute("member");
		List<Category> categoryList = getCategoryList(member.getAuthority());
		request.setAttribute("categoryList", categoryList);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/board/create.jsp");
		dispatcher.forward(request, response);
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
