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

@WebServlet("/member/deleteMember")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;utf-8");
		BufferedReader in = request.getReader();
		PrintWriter out = response.getWriter();
		JSONObject jsonIn = new JSONObject(in.readLine());
		JSONObject jsonOut = new JSONObject();
		MemberRepository memberRepository = new MemberRepository();
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		String pwd = jsonIn.getString("pwd");
		
		if (!member.getPwd().equals(pwd)) {
			jsonOut.put("status", false);
			jsonOut.put("message", "비밀번호가 일치하지 않습니다.");
			out.print(jsonOut);
			return;
		}
		session.removeAttribute("member");
		boolean dbSuccess = memberRepository.deleteMemberById(member.getId());
		jsonOut.put("status", dbSuccess);
		if (!dbSuccess) {
			jsonOut.put("message", "회원 삭제 실패");
		}
		out.print(jsonOut);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
