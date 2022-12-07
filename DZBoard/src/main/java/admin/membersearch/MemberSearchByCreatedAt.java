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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/admin/memberSearch/createdAt")
public class MemberSearchByCreatedAt extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		Timestamp from = Timestamp.valueOf(request.getParameter("from"));
		Timestamp to = Timestamp.valueOf(request.getParameter("to"));
		String last = request.getParameter("last");
		Timestamp lastCreatedAt = null;
		if (request.getParameter("lastCreatedAt") != null) {
			lastCreatedAt = Timestamp.valueOf(request.getParameter("lastCreatedAt"));
		}
		
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		MemberRepository repository = new MemberRepository(dataFactory);
		List<Member> list = null;
		if (last == null) {
			list = repository.findMembersByCreated(from, to);
		} else {
			list = repository.findMembersByCreated(from, to, last, lastCreatedAt);
		}
		
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		if (list.size() > 0) {
			Member lastMember = list.get(list.size()-1);
			jsonOut.put("more", "from="+from.toString()+"&to="+to.toString()+"&last="+lastMember.getId()+"&lastCreatedAt="+lastMember.getCreatedAt());
		}
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
