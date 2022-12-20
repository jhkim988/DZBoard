package member;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import entities.Member;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import repository.MemberRepository;
import server.Action;
import server.RequestMapping;

@Action
public class MemberAction {
	
	@RequestMapping("/member/viewMember")
	public String viewMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/member/viewMember.jsp";
	}
	
	@RequestMapping("/member/deleteMemberForm")
	public String deleteMemberForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/member/deleteMember.jsp";
	}
	
	@RequestMapping("/member/deleteMember")
	public JSONObject deleteMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		JSONObject jsonOut = new JSONObject();

		MemberRepository memberRepository = new MemberRepository();
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		String pwd = jsonIn.getString("pwd");
		
		if (!member.getPwd().equals(pwd)) {
			jsonOut.put("status", false);
			jsonOut.put("message", "비밀번호가 일치하지 않습니다.");
			return jsonOut;
		}
		
		session.removeAttribute("member");
		boolean dbSuccess = memberRepository.deleteMemberById(member.getId());
		jsonOut.put("status", dbSuccess);
		if (!dbSuccess) {
			jsonOut.put("message", "회원 삭제 실패");
		}
		return jsonOut;
	}
	
	@RequestMapping("/member/dupMemberCheck")
	public JSONObject dupMemberCheck(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		JSONObject jsonResult = new JSONObject();
		
		String type= jsonIn.getString("type");
		String value = jsonIn.getString("value");
		
		MemberRepository memberRepository = new MemberRepository();
		Member member = null;
		if ("id".equals(type)) {
			member = memberRepository.findOneMemberById(value);
		} else if ("phone".equals(type)) {
			member = memberRepository.findOneMemberByPhone(value);
		} else if ("email".equals(type)) {
			member = memberRepository.findOneMemberByEmail(value);
		} else {
			throw new UnsupportedOperationException();
		}
		
		if (member == null) {
			jsonResult.put("status", true);
			jsonResult.put("message", "사용 가능합니다.");
		} else {
			jsonResult.put("status", false);
			jsonResult.put("message", "이미 등록된 정보입니다.");
			jsonResult.put("id", member.getId());
		}
		return jsonResult;
	}
	
	@RequestMapping("/member/findIdForm")
	public String findIdForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/member/findId.html";
	}
	
	@RequestMapping("/member/findId")
	public JSONObject findId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		BufferedReader in = request.getReader();
		
		JSONObject jsonIn = new JSONObject(in.readLine());
		JSONObject jsonOut = new JSONObject();
		
		String type = jsonIn.getString("type");
		String first = jsonIn.getString("first");
		String second = jsonIn.getString("second");
	
		MemberRepository memberRepository = new MemberRepository();
		Member member = null;
		
		if ("findIdByPhone".equals(type)) {
			member = memberRepository.findOneMemberByPhone(second);
		} else if ("findIdByEmail".equals(type)) {
			member = memberRepository.findOneMemberByEmail(second);
		} else {
			throw new UnsupportedOperationException();
		}

		
		if (member == null) {
			jsonOut.put("status", false);
			jsonOut.put("message", "존재하지 않는 유저입니다.");
			return jsonOut;
		}
		
		if (!member.getName().equals(first)) {
			jsonOut.put("status", false);
			jsonOut.put("message", "이름과 매칭되지 않습니다.");
			return jsonOut;
		}
		
		jsonOut.put("status", true);
		jsonOut.put("id", member.getId());
		return jsonOut;
	}
	
	@RequestMapping("/member/findPwdForm")
	public String findPwdForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/member/findPwd.html";
	}
	
	@RequestMapping("/member/findPwd")
	public JSONObject findPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());
		
		JSONObject jsonOut = new JSONObject();
		
		String type = jsonIn.getString("type");
		String id= jsonIn.getString("id");
		String first = jsonIn.getString("first");
		String second = jsonIn.getString("second");
		
		MemberRepository memberRepository = new MemberRepository();
		Member member = memberRepository.findOneMemberById(id);
		jsonOut.put("status", false);
		if (member == null) {
			jsonOut.put("message", "올바르지 않은 아이디입니다.");
			return jsonOut;
		}
		
		if ("findIdByPhone".equals(type)) {
			if (member.getName().equals(first) && member.getPhone().equals(second)) {
				jsonOut.put("status", true);
				jsonOut.put("message", member.getPwd());
			}
		} else if ("findIdByEmail".equals(type)){
			if (member.getName().equals(first) && member.getEmail().equals(second)) {
				jsonOut.put("status", true);
				jsonOut.put("message", member.getPwd());
			}
		} else {
			throw new UnsupportedOperationException();
		}
		
		if (!jsonOut.getBoolean("status")) {
			jsonOut.put("message", "올바르지 않은 정보입니다.");
		}
		return jsonOut;
	}
	
	@RequestMapping("/member/login")
	public JSONObject login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonInput = new JSONObject(in.readLine());
		
		String id = jsonInput.getString("id");
		String pwd = jsonInput.getString("pwd");
		boolean remember = jsonInput.getBoolean("remember");
		
		MemberRepository memberRepository = new MemberRepository();
		Member member = memberRepository.findOneMemberById(id);
		JSONObject jsonResult = new JSONObject();
		
		if (member == null) {
			jsonResult.put("status", false);
			jsonResult.put("message", "존재하지 않는 아이디 입니다.");
		} else if (!member.getPwd().equals(pwd)) {
			jsonResult.put("status", false);
			jsonResult.put("message", "비밀번호가 틀렸습니다.");
		} else if (member.getAuthority() < 1) {
			jsonResult.put("status", false);
			jsonResult.put("message", "로그인 제한");
		} else { // login success
			if (remember) {
				response.addCookie(new Cookie("id", member.getId()));
			}

			memberRepository.updateUpdatedAt(member);
			member = memberRepository.findOneMemberById(member.getId());
			HttpSession session = request.getSession();
			session.setAttribute("member", member);
			jsonResult.put("status", true);
		}
		return jsonResult;
	}
	
	@RequestMapping("/member/loginForm")
	public String loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = null;
		for (Cookie cookie: request.getCookies()) {
			if ("id".equals(cookie.getName())) {
				id = cookie.getValue();
			}
		}
		if (id != null) {
			request.setAttribute("cookie_id", id);
		}
		return "/resources/member/login.jsp";
	}
	
	@RequestMapping("/member/logout")
	public JSONObject logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONObject jsonOut = new JSONObject();
		
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		
		jsonOut.put("status", true);
		jsonOut.put("message", "로그아웃!");
		return jsonOut;
	}
	
	@RequestMapping("/member/registerForm")
	public String registerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/member/register.html";
	}
	
	private static final List<String> keys = Arrays.asList("name", "id", "pwd", "pwdchk", "email", "emailHost", "phonefirst", "phonemid", "phonelast");
	private final Map<String, String> info = new HashMap<>();
	@RequestMapping("/member/register")
	public JSONObject register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		BufferedReader in = request.getReader();
		JSONObject jsonIn = new JSONObject(in.readLine());

		response.setContentType("application/json;charset=utf-8");
		keys.forEach(key -> info.put(key, jsonIn.getString(key)));
		if (hasNullParameter()) {
			return resultJSON(false, "모두 입력해주세요");
		}
		
		String emailFull = new StringBuilder(info.get("email"))
				.append('@')
				.append(info.get("emailHost"))
				.toString();
		String phone = new StringBuilder(info.get("phonefirst"))
				.append(info.get("phonemid"))
				.append(info.get("phonelast"))
				.toString();
		if (!info.get("pwd").equals(info.get("pwdchk"))) {
			return resultJSON(false, "비밀번호가 일치하지 않습니다.");
		}
		Member member = Member.builder()
				.name(info.get("name"))
				.id(info.get("id"))
				.pwd(info.get("pwd"))
				.phone(phone)
				.email(emailFull)
				.build();
		MemberRepository memberRepository = new MemberRepository();
		boolean status = memberRepository.createMember(member);
		if (status) {
			return resultJSON(status, "성공!");
		} else {
			return resultJSON(status, "실패!");
		}

	}
	
	@RequestMapping("/member/updateMemberForm")
	public String updateMemberForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/resources/member/updateMember.jsp";
	}
	
	@RequestMapping("/member/updateMember")
	public JSONObject updateMember(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json;charset=utf-8");
		BufferedReader in = request.getReader();

		JSONObject jsonIn = new JSONObject(in.readLine());
		JSONObject jsonOut = new JSONObject();
		MemberRepository memberRepository = new MemberRepository();
		String pwd = jsonIn.getString("pwd");
		String name = jsonIn.getString("name");
		String email = jsonIn.getString("email");
		String phone = jsonIn.getString("phone");
		
		if (pwd == null || name == null || email == null || phone == null) {
			jsonOut.put("status", false);
			jsonOut.put("message", "입력 값을 확인해주세요");
			return jsonOut;
		}
		
		HttpSession session = request.getSession();
		Member member = (Member) session.getAttribute("member");
		member.setPwd(pwd);
		member.setName(name);
		member.setEmail(email);
		member.setPhone(phone);
		
		boolean dbSuccess = memberRepository.updateMember(member);
		jsonOut.put("status", dbSuccess);
		if (!dbSuccess) {
			jsonOut.put("message", "회원 정보 수정 실패");
		}
		return jsonOut;
	}
	
	private boolean hasNullParameter() {
		return !info.values().stream()
				.allMatch(x -> x != null);
	}
	private JSONObject resultJSON(boolean status, String message) {
		JSONObject json = new JSONObject();
		json.put("status", status);
		json.put("message", message);
		return json;
	}
}
