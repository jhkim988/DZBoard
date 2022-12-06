<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board</title>
</head>
<body>
	<c:choose>
		<c:when test='${member != null}'>
			<p>${member.name} 님 안녕하세요 </p>
			<a href="/DZBoard/member/updateMember.jsp">회원 정보 수정</a>
			<a href="/DZBoard/member/viewMember.jsp">회원 정보</a>
			<a href="/DZBoard/member/deleteMember.jsp">회원 탈퇴</a>
		<c:if test='${member.authority == 1}'>
			<a href="/DZBoard/admin/memberSearch.jsp">회원 관리</a>
		</c:if>
		</c:when>
		<c:otherwise>
			<a href="/DZBoard/member/login.html">로그인</a>	
		</c:otherwise>
	</c:choose>
	<a href="/DZBoard/board">게시판</a>
	<a href="/DZBoard/chatting">채팅</a>
</body>
</html>