<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/DZBoard/resources/member/css/login.css"/>
<title>DZBoard 로그인</title>
<script type="text/javascript" src="/DZBoard/resources/member/js/login.js"></script>
</head>
<body>
<h1>로그인</h1>
	<form>
		<label>
			<input type="text" id="id" name="id" placeholder="아이디" value="${cookie_id}" required />
		</label>
		<label>
			<input type="password" id="pwd" name="pwd" placeholder="비밀번호" required />
		</label>
		<input id="loginButton" type="submit" value="로그인"/>	
		<footer>
			<label>
				<input id="remember" class="bottom" type="checkbox">
					아이디 저장
			</label>
			<a href="/DZBoard/member/findIdForm" class="button bottom">아이디 찾기</a>
			<a href="/DZBoard/member/findPwdForm" class="button bottom">비밀번호 찾기</a>
			<a href="/DZBoard/member/registerForm" class="button bottom">회원가입</a>
		</footer>
	</form>
</body>
</html>