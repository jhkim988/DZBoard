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

@WebServlet("/member/findId")
public class FindIdServlet extends HttpServlet {
	private static final long serialVersionUID = -6912385689110059223L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;utf-8");
		BufferedReader in = request.getReader();
		PrintWriter out = response.getWriter();
		
		JSONObject jsonIn = new JSONObject(in.readLine());
		JSONObject jsonOut = new JSONObject();
		
		String type = jsonIn.getString("type");
		String first = jsonIn.getString("first");
		String second = jsonIn.getString("second");
	
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		MemberRepository memberRepository = new MemberRepository(dataFactory);
		Member member = null;
		
		if ("findIdByPhone".equals(type)) {
			member = memberRepository.findOneMemberByPhone(second);
		} else if ("findIdByEmail".equals(type)) {
			member = memberRepository.findOneMemberByEmail(second);
		} else {
			throw new UnsupportedOperationException();
		}

		
		if (member == null) {
			jsonOut.put("status", false);
			jsonOut.put("message", "존재하지 않는 유저입니다.");
			out.print(jsonOut);
			return;
		}
		
		if (!member.getName().equals(first)) {
			jsonOut.put("status", false);
			jsonOut.put("message", "이름과 매칭되지 않습니다.");
			out.print(jsonOut);
			return;
		}
		
		jsonOut.put("status", true);
		jsonOut.put("id", member.getId());
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
