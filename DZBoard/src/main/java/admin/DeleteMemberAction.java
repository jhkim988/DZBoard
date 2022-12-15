package admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MemberRepository;
import server.Action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.sql.DataSource;

import org.json.JSONObject;

@WebServlet("/admin/deleteMember")
public class DeleteMemberAction implements Action {
       
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		
		String id = request.getParameter("id");
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		
		MemberRepository repository = new MemberRepository();
		json.put("status", repository.deleteMemberById(id));
		out.print(json);
		response.sendRedirect(request.getHeader("referer"));
	}
}
