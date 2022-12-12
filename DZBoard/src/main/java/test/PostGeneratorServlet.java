package test;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.PostRepository;

import java.io.IOException;
import java.util.Random;

import javax.sql.DataSource;

import board.Post;

@WebServlet("/generatePost")
public class PostGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random rand = new Random();
		PostRepository postRepository = new PostRepository();
		for (int i = 0; i < 100; i++) {
			postRepository.createTestPost(
					Post.builder()
					.title("추천 글 테스트!" + rand.nextInt(10))
					.content("랜덤으로 추천 수를 할당했습니다.")
					.category("공지")
					.good(rand.nextInt(100))
					.build()
					, Member.builder()
					.id("user"+rand.nextInt(10))
					.build());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
