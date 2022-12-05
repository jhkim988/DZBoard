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

@WebServlet("/member/dupMemberCheck")
public class DupMemberCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;utf-8");
		PrintWriter out = response.getWriter();
		JSONObject jsonResult = new JSONObject();
		String id = request.getParameter("id");
		System.out.println("request: " + id);
		if (id == null) {
			jsonResult.put("status", false);
			jsonResult.put("message", "Invalid Input Data: [id]");
			out.print(jsonResult);
		}
		
		MemberRepository memberRepository = new MemberRepository();
		Member member = memberRepository.findOneMemberById(id);
		if (member == null) {
			jsonResult.put("status", true);
			jsonResult.put("message", "Usable ID");
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", "Duplicate ID");
		}
		out.print(jsonResult);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
