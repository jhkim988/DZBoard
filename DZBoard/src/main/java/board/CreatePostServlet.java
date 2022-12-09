package board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.Member;
import repository.MemberRepository;
import repository.PostRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sql.DataSource;

import org.json.JSONObject;

@WebServlet("/board/createPost")
public class CreatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		String title = jsonIn.getString("title");
		String category = jsonIn.getString("category");
		String content = jsonIn.getString("content");
		Post post = Post.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();
		PostRepository postRepository = new PostRepository();
		PrintWriter out = response.getWriter();
		JSONObject jsonOut = new JSONObject();
		jsonOut.put("status", postRepository.addPost(post, member));
		MemberRepository memberRepository = new MemberRepository();
		memberRepository.updateUpdatedAt(member);
		session.setAttribute("member", memberRepository.findOneMemberById(member.getId()));
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
