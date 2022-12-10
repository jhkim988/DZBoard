<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board</title>
<link rel="stylesheet" href="index.css" />
<script type="text/javascript" src="index.js"></script>
</head>
<body>
	<header>
		<h1 id=title>Douzone Board</h1>
		<c:choose>
			<c:when test='${member != null}'>
				<p id="welcome">${member.name} 님 안녕하세요</p>
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
	</header>
	<main>
		<div id="chatLink"><a href="/DZBoard/chat/chat.html">채팅 바로가기</a></div>
		<div id="boardLink"><a href="/DZBoard/board">게시판 바로가기</a></div>
		<div id="notice">
			공지사항 <a href="/DZBoard/board/category?query=공지">[더보기]</a>
			<ul>
				<c:forEach var="notice" items="${notices}">
					<li>${notice.title}</li>
				</c:forEach>
			</ul>
		</div>
		<div id="qna">
			Q&A <a href="/DZBoard/board/category?query=Q%26A">[더보기]</a>
			<ul>
				<c:forEach var="qna" items="${qnas}">
					<li>${qna.title}</li>
				</c:forEach>
			</ul>
		</div>
		<div id="general">
			일반 게시판 <a href="/DZBoard/board/category?query=일반">[더보기]</a>
			<ul>
				<c:forEach var="general" items="${generals}">
					<li>${general.title}</li>
				</c:forEach>
			</ul>
		</div>
	</main>
</body>
</html>