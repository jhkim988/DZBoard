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
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/admin/memberSearch/name")
public class MemberSearchByNameServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		String name = request.getParameter("name");
		String last = request.getParameter("last");
		
		MemberRepository repository = new MemberRepository();
		
		List<Member> list = null;
		if (last == null) {
			list = repository.findMembersByName(name);
		} else {
			list = repository.findMembersByName(name, last);
		}
		
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		if (list.size() > 0) {
			Member lastMember = list.get(list.size()-1);
			jsonOut.put("more", urlSearchParams(lastMember));
		}
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private String urlSearchParams(Member lastMember) {
		return new StringBuilder("name=")
				.append(lastMember.getName())
				.append("&last=")
				.append(lastMember.getId())
				.toString();
	}
}
