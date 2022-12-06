package member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.MemberRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 2950492920898863375L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonInput = new JSONObject(in.readLine());
		
		String id = jsonInput.getString("id");
		String pwd = jsonInput.getString("pwd");
		
		MemberRepository repository = new MemberRepository();
		Member member = repository.findOneMemberById(id);
		JSONObject jsonResult = new JSONObject();
		
		if (member == null) {
			jsonResult.put("status", false);
			jsonResult.put("message", "존재하지 않는 아이디 입니다.");
		} else if (!member.getPwd().equals(pwd)) {
			jsonResult.put("status", false);
			jsonResult.put("message", "비밀번호가 틀렸습니다.");
		} else { // login success
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			jsonResult.put("status", true);
		}
		
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
