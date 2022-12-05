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

@WebServlet("/admin/deleteMember")
public class DeleteMemberServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;utf-");
		
		String id = request.getParameter("id");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		MemberRepository repository = new MemberRepository();
		json.put("status", repository.deleteMemberById(id));
		out.print(json);
		response.sendRedirect(request.getHeader("referer"));
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
