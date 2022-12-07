package member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MemberRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONObject;

@WebServlet("/member/register")
public class RegisterMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final List<String> keys = Arrays.asList("id", "pwd", "pwdchk", "email", "emailHost", "phoneFirst", "phoneSecond", "phoneThird");
	private final Map<String, String> info = new HashMap<>();
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;utf-8");
		PrintWriter out = response.getWriter();
		keys.forEach(key -> info.put(key, request.getParameter(key)));
		if (hasNullParameter()) {
			out.print(resultJSON(false, "Required Data Omission"));
			return;
		}
		
		info.put("name", request.getParameter("name"));
		String emailFull = new StringBuilder(info.get("email"))
				.append('@')
				.append(info.get("emailHost"))
				.toString();
		String phone = new StringBuilder(info.get("phoneFirst"))
				.append(info.get("phoneSecond"))
				.append(info.get("phoneThird"))
				.toString();
		if (!info.get("pwd").equals(info.get("pwdchk"))) {
			out.print(resultJSON(false, "Password Check Error"));
			return;
		}
		Member member = Member.builder()
				.name(info.get("name"))
				.id(info.get("id"))
				.pwd(info.get("pwd"))
				.phone(phone)
				.email(emailFull)
				.build();
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		MemberRepository memberRepository = new MemberRepository(dataFactory);
		boolean status = memberRepository.addMember(member);
		out.print(resultJSON(status, member.getName()));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean hasNullParameter() {
		info.entrySet().stream().forEach(x -> System.out.println(x.getKey() + ": " + x.getValue()));
		return !info.values().stream()
				.allMatch(x -> x != null);
	}
	private JSONObject resultJSON(boolean status, String message) {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("message", message);
		return json;
	}
}
