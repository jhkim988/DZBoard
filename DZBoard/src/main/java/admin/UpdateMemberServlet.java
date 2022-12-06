package admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

@WebServlet("/admin/updateMember")
public class UpdateMemberServlet extends HttpServlet {
	private static final long serialVersionUID = -9056912404649914500L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		MemberRepository repository = new MemberRepository();
		Member member = repository.findOneMemberById(id);
		request.setAttribute("member", member);
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/admin/updateMember.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject json = new JSONObject(in.readLine());
		String id = json.getString("id");
		String pwd = json.getString("pwd");
		String name = json.getString("name");
		String email = json.getString("email");
		String phone = json.getString("phone");
		String authority = json.getString("authority");
		
		
		MemberRepository repository = new MemberRepository();
		Member member = repository.findOneMemberById(id);
		member.setPwd(pwd);
		member.setName(name);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAuthority(Integer.parseInt(authority));
		
		boolean result = repository.updateMember(member);
		json.put("status", result);
		if (!result) {
			json.put("message", "Member Not Found");
		}
		
		response.setContentType("application/json;utf-8");
		PrintWriter out = response.getWriter();
		out.print(json);
	}
}
