package member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.Member;
import repository.MemberRepository;
import server.Action;

public class LoginAction implements Action {
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonInput = new JSONObject(in.readLine());
		
		String id = jsonInput.getString("id");
		String pwd = jsonInput.getString("pwd");
		boolean remember = jsonInput.getBoolean("remember");
		
		MemberRepository memberRepository = new MemberRepository();
		Member member = memberRepository.findOneMemberById(id);
		JSONObject jsonResult = new JSONObject();
		
		if (member == null) {
			jsonResult.put("status", false);
			jsonResult.put("message", "존재하지 않는 아이디 입니다.");
		} else if (!member.getPwd().equals(pwd)) {
			jsonResult.put("status", false);
			jsonResult.put("message", "비밀번호가 틀렸습니다.");
		} else if (member.getAuthority() < 1) {
			jsonResult.put("status", false);
			jsonResult.put("message", "로그인 제한");
		} else { // login success
			if (remember) {
				response.addCookie(new Cookie("id", member.getId()));
			}

			memberRepository.updateUpdatedAt(member);
			member = memberRepository.findOneMemberById(member.getId());
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			jsonResult.put("status", true);
		}
		PrintWriter out = response.getWriter();
		out.print(jsonResult);
	}
}
