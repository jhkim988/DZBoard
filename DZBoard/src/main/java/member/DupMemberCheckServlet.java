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

import javax.sql.DataSource;

import org.json.JSONObject;

@WebServlet("/member/dupMemberCheck")
public class DupMemberCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		PrintWriter out = response.getWriter();
		JSONObject jsonResult = new JSONObject();
		
		String type= jsonIn.getString("type");
		String value = jsonIn.getString("value");
		
		MemberRepository memberRepository = new MemberRepository();
		Member member = null;
		if ("id".equals(type)) {
			member = memberRepository.findOneMemberById(value);
		} else if ("phone".equals(type)) {
			member = memberRepository.findOneMemberByPhone(value);
		} else if ("email".equals(type)) {
			member = memberRepository.findOneMemberByEmail(value);
		} else {
			throw new UnsupportedOperationException();
		}
		
		if (member == null) {
			jsonResult.put("status", true);
			jsonResult.put("message", "사용 가능합니다.");
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", "이미 등록된 정보입니다.");
			jsonResult.put("id", member.getId());
		}
		out.print(jsonResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
