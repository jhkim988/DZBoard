<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board</title>
<link rel="stylesheet" href="/DZBoard/resources/home.css" />
<script type="text/javascript" src="/DZBoard/resources/home.js"></script>
</head>
<body>
	<header>
		<div id="member">
		<c:choose>
			<c:when test='${member != null}'>
				<p id="welcome">${member.name} 님 안녕하세요</p>
				<a href="/DZBoard/member/viewMember">회원 정보</a>
				<c:if test='${member.authority == 99}'>
					<a href="/DZBoard/admin/index">관리자 페이지</a>
				</c:if>
				<button id="logout">로그아웃</button>
			</c:when>
			<c:otherwise>
				<a href="/DZBoard/member/loginForm">로그인</a>
			</c:otherwise>
		</c:choose>
		</div>
		<h1 id=title>Douzone Board</h1>
	</header>
	<nav>
		<a href="/DZBoard/chat/">채팅 바로가기</a>
		<a href="/DZBoard/board">게시판 바로가기</a>
	</nav>
	<main>
		<div id="notice">
			공지사항 <a href="/DZBoard/board/category?query=공지">[더보기]</a>
			<ul>
				<c:forEach var="notice" items="${notices}">
					<li><a href="/DZBoard/board/view?id=${notice.id}">${notice.title}</a></li>
				</c:forEach>
			</ul>
		</div>
		<div id="qna">
			Q&A <a href="/DZBoard/board/category?query=Q%26A">[더보기]</a>
			<ul>
				<c:forEach var="qna" items="${qnas}">
					<li><a href="/DZBoard/board/view?id=${qna.id}">${qna.title}</a></li>
				</c:forEach>
			</ul>
		</div>
		<div id="general">
			일반 게시판 <a href="/DZBoard/board/category?query=일반">[더보기]</a>
			<ul>
				<c:forEach var="general" items="${generals}">
					<li><a href="/DZBoard/board/view?id=${general.id}">${general.title}</a></li>
				</c:forEach>
			</ul>
		</div>
	</main>
</body>
</html>