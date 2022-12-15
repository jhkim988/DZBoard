package admin;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;
import server.Action;

public class UpdateMemberFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		MemberRepository repository = new MemberRepository();
		Member member = repository.findOneMemberById(id);
		request.setAttribute("member", member);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/admin/updateMember.jsp");
		dispatcher.forward(request, response);
	}

}
