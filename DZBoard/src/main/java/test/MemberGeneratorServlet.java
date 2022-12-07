package test;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;

import java.io.IOException;

import javax.sql.DataSource;

@WebServlet("/generateMember")
public class MemberGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		MemberRepository repository = new MemberRepository(dataFactory);
		for (int i = 100; i < 1000; i++) {
			int rand = (int) (Math.random()*10);
			repository.addMember(Member.builder()
					.id("user0"+i)
					.pwd("pwd")
					.name("자바좋아"+rand)
					.email("java" + i + "@naver.com")
					.phone("010111100"+i)
					.build());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
