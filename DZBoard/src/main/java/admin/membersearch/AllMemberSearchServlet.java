package admin.membersearch;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/admin/memberSearch/allMember")
public class AllMemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		
		String last = request.getParameter("last");

		MemberRepository repository = new MemberRepository();
		List<Member> list = null;
		if (last == null) {
			list = repository.findMembersAll();
		} else {
			list = repository.findMembersAll(last);
		}
		
		
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		if (list.size() > 0) {
			Member lastMember = list.get(list.size()-1);
			jsonOut.put("more", urlSearchParams(lastMember));
		} else {
			jsonOut.put("more", "");
		}
		
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String urlSearchParams(Member lastMember) {
		return new StringBuilder("last=")
				.append(lastMember.getId())
				.toString();
	}
}
