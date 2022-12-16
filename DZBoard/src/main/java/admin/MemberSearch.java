package admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.json.JSONArray;
import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;

public class MemberSearch {
	
	public JSONObject searchAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String last = request.getParameter("last");

		MemberRepository repository = new MemberRepository();
		List<Member> list = last == null
							? repository.findMembersAll()
							: repository.findMembersAll(last);
		
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		jsonOut.put("more", "");
		if (list.size() > 0) {
			Member lastMember = list.get(list.size()-1);
			Map<String, Object> param = new HashMap<>();
			param.put("last", lastMember.getId());
			jsonOut.put("more", urlSearchParams(param));
		}
		return jsonOut;
	}
	
	public JSONObject searchByCreatedAt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Timestamp from = Timestamp.valueOf(Objects.requireNonNull(request.getParameter("from")));
		Timestamp to = Timestamp.valueOf(Objects.requireNonNull(request.getParameter("to")));
		
		String last = request.getParameter("last");
		Timestamp lastCreatedAt = null;
		if (request.getParameter("lastCreatedAt") != null) {
			lastCreatedAt = Timestamp.valueOf(request.getParameter("lastCreatedAt"));
		}
		
		MemberRepository repository = new MemberRepository();
		List<Member> list = last == null
							? repository.findMembersByCreated(from, to)
							: repository.findMembersByCreated(from, to, last, lastCreatedAt);
		
		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		jsonOut.put("more", "");
		if (list.size() > 0) {
			Member lastMember = list.get(list.size()-1);
			Map<String, Object> param = new HashMap<>();
			param.put("from", from);
			param.put("to", to);
			param.put("last", lastMember.getId());
			param.put("lastCreatedAt", lastMember.getCreatedAt());
			jsonOut.put("more", urlSearchParams(param));
		}
		return jsonOut;
	}
	
	public JSONObject searchByEmail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = Objects.requireNonNull(request.getParameter("email"));
		MemberRepository repository = new MemberRepository();

		List<Member> list = new ArrayList<>();
		Member member = Objects.requireNonNull(repository.findOneMemberByEmail(email));
		list.add(member);

		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		return jsonOut;
	}
	
	public void searchById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		String id = Objects.requireNonNull(request.getParameter("id"));
		MemberRepository repository = new MemberRepository();

		List<Member> list = new ArrayList<>();
		Member member = Objects.requireNonNull(repository.findOneMemberById(id));
		list.add(member);

		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		out.print(jsonOut);
	}
	
	public JSONObject searchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = Objects.requireNonNull(request.getParameter("name"));
		String last = request.getParameter("last");
		
		MemberRepository repository = new MemberRepository();
		List<Member> list = last == null
				? list = repository.findMembersByName(name)
				: repository.findMembersByName(name, last);

		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		jsonOut.put("more", "");
		if (list.size() > 0) {
			Member lastMember = list.get(list.size()-1);
			Map<String, Object> param = new HashMap<>();
			param.put("name", lastMember.getName());
			param.put("last", lastMember.getId());
			jsonOut.put("more", urlSearchParams(param));
		}
		return jsonOut;
	}
	
	public JSONObject searchByPhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String phone = Objects.requireNonNull(request.getParameter("phone"));
		MemberRepository repository = new MemberRepository();

		List<Member> list = new ArrayList<>();
		Member member = Objects.requireNonNull(repository.findOneMemberByPhone(phone));
		list.add(member);

		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		return jsonOut;
	}
	
	public JSONObject searchByUpdatedAt (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Timestamp from = Objects.requireNonNull(Timestamp.valueOf(request.getParameter("from")));
		Timestamp to = Objects.requireNonNull(Timestamp.valueOf(request.getParameter("to")));
		String last = request.getParameter("last");

		Timestamp lastUpdatedAt = null;
		if (request.getParameter("lastUpdatedAt") != null) {
			lastUpdatedAt = Timestamp.valueOf(request.getParameter("lastUpdatedAt"));
		}
		MemberRepository repository = new MemberRepository();
		List<Member> list = last == null
							? repository.findMembersByUpdated(from, to)
							: repository.findMembersByUpdated(from, to, last, lastUpdatedAt);

		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		jsonOut.put("more", "");
		if (list.size() > 0) {
			Member lastMember = list.get(list.size() - 1);
			Map<String, Object> param = new HashMap<>();
			param.put("from", from);
			param.put("to", to);
			param.put("last", lastMember.getId());
			param.put("lastUpdatedAt", lastMember.getUpdatedAt());
			jsonOut.put("more", urlSearchParams(param));
		}
		return jsonOut;
	}
	
	public JSONObject searchByAuthority(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String level = Objects.requireNonNull(request.getParameter("level"));
		String last = request.getParameter("last");

		MemberRepository repository = new MemberRepository();
		List<Member> list = last == null
				? repository.findMembersByAuthority(level)
				: repository.findMembersByAuthority(level, last);

		JSONObject jsonOut = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		list.forEach(x -> jsonArr.put(x.toJSONObject()));
		jsonOut.put("status", true);
		jsonOut.put("data", jsonArr);
		jsonOut.put("more", "");
		if (list.size() > 0) {
			Member lastMember = list.get(list.size() - 1);
			Map<String, Object> param = new HashMap<>();
			param.put("level", level);
			param.put("last", lastMember.getId());
			jsonOut.put("more", urlSearchParams(param));
		}
		return jsonOut;
	}
	
	private String urlSearchParams(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		map.forEach((k, v) -> sb.append(k).append('=').append(v.toString()).append('&'));
		return sb.substring(0, sb.length()-1);
	}
}
