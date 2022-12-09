<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board</title>
<script type="text/javascript" src="index.js"></script>
</head>
<body>
	<c:choose>
		<c:when test='${member != null}'>
			<p>${member.name} 님 안녕하세요 </p>
			<a href="/DZBoard/member/viewMember.jsp">회원 정보</a>
		<c:if test='${member.authority == 99}'>
			<a href="/DZBoard/admin/index.html">관리자 페이지</a>
		</c:if>
		<button id="logout">로그아웃</button>
		</c:when>
		<c:otherwise>
			<a href="/DZBoard/member/login.html">로그인</a>	
		</c:otherwise>
	</c:choose>
	<a href="/DZBoard/board">게시판</a>
	<a href="/DZBoard/chat/chat.html">채팅</a>
</body>
</html>