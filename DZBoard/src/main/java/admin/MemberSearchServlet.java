package admin;

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
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/admin/memberSearch")
public class MemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject json = new JSONObject(in.readLine());
		String type = json.getString("type");
		String first = json.getString("first");
		String second = json.getString("second");
		boolean asc = json.getBoolean("asc");
		int page = json.getInt("page");
		int limit = json.getInt("limit");
		String last = null;
		if (json.getBoolean("hasLast")) {
			last = json.getString("last");
		}
		
		MemberRepository repository = new MemberRepository();
		List<Member> list = new ArrayList<>();
		
		if ("all".equals(type)) {
			list = repository.findMembersAll(page, limit, last, asc);
		}  else if ("name".equals(type)) {
			list = repository.findMembersByName(first, page, limit, last, asc);
		}   else if ("createdAt".equals(type)) {
			list = repository.findMembersByCreated(first, second, page, limit, last, asc);
		} else if ("updatedAt".equals(type)) {
			list = repository.findMembersByUpdated(first, second, page, limit, last, asc);
		} else if ("authority".equals(type)) {
			list = repository.findMembersByAuthority(first, page, limit, last, asc);
		} else if ("id".equals(type)) {
			list.add(repository.findOneMemberById(first));
		} else if ("email".equals(type)) {
			list.add(repository.findOneMemberByEmail(first));
		} else if ("phone".equals(type)) {
			list.add(repository.findOneMemberByPhone(first));
		} else {
			throw new UnsupportedOperationException();
		}

		PrintWriter out = response.getWriter();
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		out.print(jsonOut);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
