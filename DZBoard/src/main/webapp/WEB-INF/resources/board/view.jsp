<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board - ${post.title}</title>
<script type="text/javascript" src="/DZBoard/resources/board/js/view.js"></script>
<link rel="stylesheet" href="/DZBoard/resources/board/css/postform.css"/>
</head>
<body>
<p>글 번호: ${post.id}</p>
<h1>제목: ${post.title}</h1>
<p>카테고리: ${post.category}</p>
<p>작성자: ${post.author}</p>
<p>조회수: ${post.viewcount}</p>
<p>작성 시간: ${post.createdAt}</p>
<p>${post.content}</p>
<ul>
	<c:forEach var="file" items="${uploadedFiles}">
		<li>${file.org_name} <a href="/DZBoard/board/download?f_id=${file.f_id}">다운로드</a></li>
	</c:forEach>
</ul>
<p><button id = "goodButton">추천</button> ${post.good}</p>
<p><button id = "badButton">비추천</button> ${post.bad}</p>
<a href="/DZBoard/board/updateForm?id=${post.id}">수정</a>
<a href="/DZBoard/board/deletePost?id=${post.id}">삭제</a>
<c:if test="${post.id == post.parent}">
	<a href="/DZBoard/board/replyForm?parent=${post.parent}">답글쓰기</a>
</c:if>
<a href="/DZBoard/board">뒤로가기</a>
</body>
</html>