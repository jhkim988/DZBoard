package board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.GoodBadRepository;
import repository.PostRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

@WebServlet("/board/goodBad")
public class GoodBadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
      
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		String type = jsonIn.getString("type");
		int postId = jsonIn.getInt("postid");
		Member member = (Member) request.getSession().getAttribute("member");
		
		PostRepository postRepository = new PostRepository();
		GoodBadRepository goodBadRepository = new GoodBadRepository();
		
		JSONObject jsonOut = new JSONObject();
		jsonOut.put("message", "이미 클릭하셨습니다.");
		if ("good".equals(type)) {
			if (goodBadRepository.click("y", member.getId(), postId)) {
				postRepository.incrementGood(postId);
				jsonOut.put("message", "추천!");
			}
		} else if ("bad".equals(type)) {
			if (goodBadRepository.click("n", member.getId(), postId)) {
				postRepository.incrementBad(postId);
				jsonOut.put("message", "비추천!");
			}
		} else {
			throw new UnsupportedOperationException();
		}
		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
