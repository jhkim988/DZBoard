<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board 회원 정보 수정</title>
<script type="text/javascript" src="./js/updateMember.js"></script>
<link rel="stylesheet" href="./css/updateMember.css"/>
</head>
<body>
	<table>
		<tr>
			<td class="key">아이디:</td>
			<td class="value" id="id">${member.id}</td>
		</tr>
		<tr>
			<td class="key">비밀번호:</td>
			<td class="value"><input id="pwd" type="password"/></td>
		</tr>
		<tr>
			<td class="key">비밀번호 확인:</td>
			<td class="value"><input id="pwdchk" type="password"/></td>
		</tr>
		<tr>
			<td class="key">이름:</td>
			<td class="value"><input id="name" type="text" value="${member.name}"/></td>
		</tr>
		<tr>
			<td class="key">이메일:</td>
			<td class="value"><input id="email" type="text" value="${member.email}"/></td>
		</tr>
		<tr>
			<td class="key">연락처:</td>
			<td class="value"><input id="phone" type="text" value="${member.phone}"/></td>
		</tr>
		<tr>
			<td class="key">회원 가입 일자:</td>
			<td class="value">${member.createdAt}</td>
		</tr>
		<tr>
			<td class="key">최근 활동 일자:</td>
			<td class="value">${member.updatedAt}</td>
		</tr>
		<tr>
			<td class="key">권한:</td>
			<td class="value">${member.authority}</td>
		</tr>
	</table>
	<footer>
		<button id="updateButton">수정</button>
		<a href="/DZBoard/pages/member/viewMember.jsp">뒤로 가기</a>
	</footer>
</body>
</html>