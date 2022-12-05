package member;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import repository.MemberRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/admin/memberList")
public class MemberSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject json = new JSONObject(in.readLine());
		String type = json.getString("type");
		String first = json.getString("first");
		String second = json.getString("second");

		MemberRepository repository = new MemberRepository();
		List<Member> list = new ArrayList<>();
		
		if ("all".equals(type)) {
			list = repository.findMembers();
		} else if ("id".equals(type)) {
			list.add(repository.findOneMemberById(first));
		} else if ("name".equals(type)) {
			list = repository.findMembersByName(first);
		} else if ("email".equals(type)) {
			list.add(repository.findOneMemberByEmail(first));
		} else if ("phone".equals(type)) {
			list.add(repository.findOneMemberByPhone(first));
		} else if ("createdAt".equals(type)) {
			list = repository.findMembersByCreated(first, second);
		} else if ("updatedAt".equals(type)) {
			list = repository.findMembersByUpdated(first, second);
		} else if ("authority".equals(type)) {
			list = repository.findMembersByAuthority(first);
		} else {
			throw new UnsupportedOperationException();
		}
		responseUsers(response, list);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void responseUsers(HttpServletResponse response, List<Member> list) throws IOException {
		response.setContentType("application/json;utf-8");
		PrintWriter out = response.getWriter();
		JSONObject json = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		list.stream()
			.filter(member -> member != null)
			.forEach(member -> jsonArray.put(member.toJSONObject()));
		json.put("status", true);
		json.put("data", jsonArray);
		out.print(json);
	}
	
}
