package board;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.Member;
import repository.PostRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.sql.DataSource;

import org.json.JSONObject;

@WebServlet("/board/updatePost")
public class UpdatePostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		HttpSession session = request.getSession();
		Member loginMember = (Member) session.getAttribute("member");
		int postId = jsonIn.getInt("id");
	
		PostRepository postRepository = new PostRepository();
		Post oldPost = postRepository.findOnePostById(postId);
		
		String title = jsonIn.getString("title");
		String content = jsonIn.getString("content");
		String category = jsonIn.getString("category");
		Post newPost = Post.builder()
				.title(title)
				.content(content)
				.category(category)
				.build();
		
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		if (oldPost != null && oldPost.isSameAuthor(loginMember) && postRepository.updatePost(oldPost, newPost)) {
			json.put("status", true);
			json.put("message", "수정!");
			json.put("url", "/DZBoard/board/view?id="+postId);
		} else {
			json.put("status", false);
			json.put("message", "실패!");
			json.put("url", "/DZBoard/board/updatePost?id="+postId);
		}
		out.print(json);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
