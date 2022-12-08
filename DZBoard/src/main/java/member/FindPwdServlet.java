package member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MemberRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

@WebServlet("/member/findPwd")
public class FindPwdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		PrintWriter out = response.getWriter();
		JSONObject jsonOut = new JSONObject();
		
		String type = jsonIn.getString("type");
		String id= jsonIn.getString("id");
		String first = jsonIn.getString("first");
		String second = jsonIn.getString("second");
		
		MemberRepository memberRepository = new MemberRepository();
		Member member = memberRepository.findOneMemberById(id);
		jsonOut.put("status", false);
		if (member == null) {
			jsonOut.put("message", "올바르지 않은 아이디입니다.");
			out.print(jsonOut);
			return;
		}
		
		if ("findIdByPhone".equals(type)) {
			if (member.getName().equals(first) && member.getPhone().equals(second)) {
				jsonOut.put("status", true);
				jsonOut.put("message", member.getPwd());
			}
		} else if ("findIdByEmail".equals(type)){
			if (member.getName().equals(first) && member.getEmail().equals(second)) {
				jsonOut.put("status", true);
				jsonOut.put("message", member.getPwd());
			}
		} else {
			throw new UnsupportedOperationException();
		}
		
		if (!jsonOut.getBoolean("status")) {
			jsonOut.put("message", "올바르지 않은 정보입니다.");
		}
		
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
