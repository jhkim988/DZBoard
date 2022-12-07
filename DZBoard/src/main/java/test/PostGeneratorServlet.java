package test;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.PostRepository;

import java.io.IOException;

import javax.sql.DataSource;

import board.Post;

@WebServlet("/generatePost")
public class PostGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		PostRepository postRepository = new PostRepository(dataFactory);
		for (int i = 0; i < 1000; i++) {
			postRepository.addPost(
					Post.builder().title("test" + i).content("test" + i).category("테스트").build()
					, Member.builder().id("user01").build());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
