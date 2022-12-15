package admin.membersearch;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;
import server.Action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet()
public class MemberSearchByEmailAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String email = request.getParameter("email");
		MemberRepository repository = new MemberRepository();

		List<Member> list = new ArrayList<>();
		Member member = repository.findOneMemberByEmail(email);
		if (member != null) {
			list.add(member);
		}

		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		out.print(jsonOut);
	}
}
