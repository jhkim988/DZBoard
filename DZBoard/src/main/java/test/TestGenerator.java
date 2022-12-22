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
		for (int i = 0; i <= 1000; i++) {
			postRepository.createTestPost(
					Post.builder()
					.title(Integer.toString(i))
					.parent(i)
					.content("검색어 테스트")
					.category("공지")
					.build()
					, Member.builder()
					.id("user"+rand.nextInt(10))
					.build());
		}
		return "/resources/index";
	}
}
