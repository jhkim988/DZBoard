<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="./board/board.js"></script>
</head>
<body>
<a href="/DZBoard/board/create.jsp">글쓰기</a>
<a href="/DZBoard">메인화면</a>
	<table>
		<thead>
		<tr>
			<th>글번호</th>
			<th>분류</th>
			<th>제목</th>
			<th>작성자</th>
			<th>조회수</th>
			<th>추천</th>
			<th>비추천</th>
			<th>작성시간</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var='post' items='${posts}'>
			<tr>
				<td class='id'>${post.id}</td>
				<td>${post.category}</td>
				<td><a href="/DZBoard/board/view?id=${post.id}">${post.title}</a></td>
				<td>${post.author}</td>
				<td>${post.viewcount}</td>
				<td>${post.good}</td>
				<td>${post.bad}</td>
				<td class='createdAt'>${post.createdAt}</td>
			</tr>			
		</c:forEach>
		</tbody>
	</table>
	<a id="prev" href="">이전</a>
	<a id="next" href="">다음</a>
</body>
</html>