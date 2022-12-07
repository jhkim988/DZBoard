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

import javax.sql.DataSource;

import org.json.JSONObject;

@WebServlet("/member/updateMember")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1849937445826201642L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;utf-8");
		BufferedReader in = request.getReader();
		PrintWriter out = response.getWriter();
		JSONObject jsonIn = new JSONObject(in.readLine());
		JSONObject jsonOut = new JSONObject();
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		MemberRepository memberRepository = new MemberRepository(dataFactory);
		System.out.println("HERE");
		String pwd = jsonIn.getString("pwd");
		String name = jsonIn.getString("name");
		String email = jsonIn.getString("email");
		String phone = jsonIn.getString("phone");
		
		if (pwd == null || name == null || email == null || phone == null) {
			jsonOut.put("status", false);
			jsonOut.put("message", "입력 값을 확인해주세요");
			out.print(jsonOut);
			return;
		}
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		member.setPwd(pwd);
		member.setName(name);
		member.setEmail(email);
		member.setPhone(phone);
		
		boolean dbSuccess = memberRepository.updateMember(member);
		jsonOut.put("status", dbSuccess);
		if (!dbSuccess) {
			jsonOut.put("message", "회원 정보 수정 실패");
		}
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
