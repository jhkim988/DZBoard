package test;

import java.io.IOException;
import java.util.Random;

import entities.Member;
import entities.Post;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MemberRepository;
import repository.PostRepository;
import server.Action;
import server.RequestMapping;

@Action
public class TestGenerator {

	@RequestMapping("/generateMember")
	public String memberGenerator(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Random rand = new Random();
		MemberRepository repository = new MemberRepository();
		for (int i = 0; i < 50; i++) {
			repository.createMember(Member.builder()
					.id("user" + i)
					.pwd("pwd")
					.name("자바좋아" + rand.nextInt(10))
					.email("java" + i + "@google.com")
					.phone("0100000000" + i)
					.build());
		}
		return "/resources/index";
	}
	
	@RequestMapping("/generatePost")
	public String postGenerator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random rand = new Random();
		PostRepository postRepository = new PostRepository();
		for (int i = 300_002; i <= 400_000; i++) {
			postRepository.createTestPost(
					Post.builder()
					.title("페이징 테스트" + rand.nextInt(10))
					.parent(i)
					.content("글을 엄청 많이 써보자")
					.category("일반")
					.build()
					, Member.builder()
					.id("user"+rand.nextInt(10))
					.build());
		}
		return "/resources/index";
	}
}
