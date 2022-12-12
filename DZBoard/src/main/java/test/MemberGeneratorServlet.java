package test;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;

import java.io.IOException;
import java.util.Random;

import javax.sql.DataSource;

@WebServlet("/generateMember")
public class MemberGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Random rand = new Random();
		MemberRepository repository = new MemberRepository();
		for (int i = 0; i < 50; i++) {
			repository.createMember(Member.builder()
					.id("user"+i)
					.pwd("pwd")
					.name("자바좋아"+rand.nextInt(10))
					.email("java" + i + "@google.com")
					.phone("0100000000"+i)
					.build());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
