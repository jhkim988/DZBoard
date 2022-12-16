package admin;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONObject;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.Member;
import repository.MemberRepository;

public class MemberManage {
	public String deleteMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		String id = request.getParameter("id");
		MemberRepository repository = new MemberRepository();
		repository.deleteMemberById(id);
		return request.getHeader("referer");
	}
	
	public String  updateMemberForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		MemberRepository repository = new MemberRepository();
		Member member = repository.findOneMemberById(id);
		request.setAttribute("manage_member", member);
		return "/resources/admin/updateMember.jsp";
	}
	
	public JSONObject updateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject json = new JSONObject(in.readLine());
		String id = json.getString("id");
		String pwd = json.getString("pwd");
		String name = json.getString("name");
		String email = json.getString("email");
		String phone = json.getString("phone");
		String authority = json.getString("authority");

		MemberRepository repository = new MemberRepository();
		Member member = repository.findOneMemberById(id);
		member.setPwd(pwd);
		member.setName(name);
		member.setEmail(email);
		member.setPhone(phone);
		member.setAuthority(Integer.parseInt(authority));

		JSONObject jsonOut = new JSONObject();
		boolean result = repository.updateMember(member);
		jsonOut.put("status", result);
		if (!result) {
			jsonOut.put("message", "Member Not Found");
		}
		return jsonOut;
	}
}
