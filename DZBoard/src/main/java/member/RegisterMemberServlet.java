package member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MemberRepository;

import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

@WebServlet("/register")
public class RegisterMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;utf-8");
		PrintWriter out = response.getWriter();
		if (hasNullParameter(request)) {
			out.print(resultJSON(false, "Required Data Omission"));
			return;
		}
		String name = request.getParameter("name");
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String pwdChk = request.getParameter("pwdchk");
		String phoneFirst = request.getParameter("phoneFirst");
		String phoneSecond = request.getParameter("phoneSecond");
		String phoneThird = request.getParameter("phoneThird");
		String phone = new StringBuilder(phoneFirst)
				.append(phoneSecond)
				.append(phoneThird)
				.toString();
		if (!pwd.equals(pwdChk)) {
			out.print(resultJSON(false, "Password Check Error"));
			return;
		}
		Member member = Member.builder()
				.name(name)
				.id(id)
				.pwd(pwd)
				.phone(phone)
				.build();
		MemberRepository memberRepository = new MemberRepository();
		boolean status = memberRepository.addMember(member);
		out.print(resultJSON(status, member.getName()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean hasNullParameter(HttpServletRequest request) {
		return request.getParameterMap().values().stream()
					.allMatch(x -> x != null);
	}
	private JSONObject resultJSON(boolean status, String message) {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("message", message);
		return json;
	}
}
