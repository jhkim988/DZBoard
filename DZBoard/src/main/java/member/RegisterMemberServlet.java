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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.json.JSONObject;

@WebServlet("/member/register")
public class RegisterMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final List<String> keys = Arrays.asList("name", "id", "pwd", "pwdchk", "email", "emailHost", "phonefirst", "phonemid", "phonelast");
	private final Map<String, String> info = new HashMap<>();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());

		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		keys.forEach(key -> info.put(key, jsonIn.getString(key)));
		if (hasNullParameter()) {
			out.print(resultJSON(false, "모두 입력해주세요"));
			return;
		}
		
		String emailFull = new StringBuilder(info.get("email"))
				.append('@')
				.append(info.get("emailHost"))
				.toString();
		String phone = new StringBuilder(info.get("phonefirst"))
				.append(info.get("phonemid"))
				.append(info.get("phonelast"))
				.toString();
		if (!info.get("pwd").equals(info.get("pwdchk"))) {
			out.print(resultJSON(false, "비밀번호가 일치하지 않습니다."));
			return;
		}
		Member member = Member.builder()
				.name(info.get("name"))
				.id(info.get("id"))
				.pwd(info.get("pwd"))
				.phone(phone)
				.email(emailFull)
				.build();
		MemberRepository memberRepository = new MemberRepository();
		boolean status = memberRepository.createMember(member);
		if (status) {
			out.print(resultJSON(status, "성공!"));
		} else {
			out.print(resultJSON(status, "실패!"));
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private boolean hasNullParameter() {
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
