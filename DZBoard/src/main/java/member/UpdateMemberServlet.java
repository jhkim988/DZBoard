package member;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MemberRepository;

import java.io.IOException;

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
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String authority = request.getParameter("authority");
		
		JSONObject json = new JSONObject();
		
		MemberRepository repository = new MemberRepository();
		Member member = repository.findOneMemberById(id);
		member.setPwd(pwd);
		member.setName(name);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAuthority(Integer.parseInt(authority));
		json.put("status", repository.updateMember(member));
		response.sendRedirect("/admin/memberSearch");
	}

}
