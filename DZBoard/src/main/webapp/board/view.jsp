<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board - ${post.title}</title>
<script type="text/javascript" src="view.js"></script>
</head>
<body>
<p>글 번호: ${post.id}</p>
<h1>제목: ${post.title}</h1>
<p>카테고리: ${post.category}</p>
<p>작성자: ${post.author}</p>
<p>조회수: ${post.viewcount}</p>
<p>작성 시간: ${post.createdAt}</p>
<p>${post.content}</p>
<p><button id = "goodButton">추천</button> ${post.good}</p>
<p><button id = "badButton">비추천</button> ${post.bad}</p>
<a href="/DZBoard/board/updateForm?id=${post.id}">수정</a>
<a href="/DZBoard/board/deletePost?id=${post.id}">삭제</a>
<a href="/DZBoard/board">뒤로가기</a>
</body>
</html>