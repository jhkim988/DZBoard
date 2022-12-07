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
import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/admin/memberSearch/updatedAt")
public class MemberSearchByUpdatedAt extends HttpServlet {
	private static final long serialVersionUID = 3636873689938218543L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		Timestamp from = Timestamp.valueOf(request.getParameter("from"));
		Timestamp to = Timestamp.valueOf(request.getParameter("to"));
		String last = request.getParameter("last");
		
		Timestamp lastUpdatedAt = null;
		if (request.getParameter("lastUpdatedAt") != null) {
			lastUpdatedAt = Timestamp.valueOf(request.getParameter("lastUpdatedAt"));
		}
		System.out.println("lastUpdatedAt: " + lastUpdatedAt);
		DataSource dataFactory = (DataSource) getServletContext().getAttribute("dataFactory");
		MemberRepository repository = new MemberRepository(dataFactory);
		List<Member> list = null;
		if (last == null) {
			list = repository.findMembersByUpdated(from, to);
		} else {
			list = repository.findMembersByUpdated(from, to, last, lastUpdatedAt);
		}
		
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		System.out.println("list.size(): " + list.size());
		if (list.size() > 0) {
			Member lastMember = list.get(list.size()-1);
			jsonOut.put("more", "from="+from.toString()+"&to="+to.toString()+"&last="+lastMember.getId()+"&lastUpdatedAt="+lastMember.getUpdatedAt());
		}
		out.print(jsonOut);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
