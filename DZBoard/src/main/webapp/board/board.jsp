<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board - 게시판</title>
<link rel="stylesheet" href="/DZBoard/board/board.css" />
<script type="text/javascript" src="/DZBoard/board/board.js"></script>
</head>
<body>
	<h1><a href="/DZBoard/index">Douzone Board</a></h1>
	<table id="board">
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
		<tbody id="notice">
			<c:forEach var='notice' items='${notices}'>
				
			</c:forEach>
		</tbody>
		<tbody id="mainBoard">
			<c:forEach var='post' items='${posts}'>
				<tr>
					<td class='id'>${post.id}</td>
					<td class='category'>${post.category}</td>
					<td class='title'><a href="/DZBoard/board/view?id=${post.id}">${post.title}</a></td>
					<td class='author'>${post.author}</td>
					<td class='viewcount'>${post.viewcount}</td>
					<td class='good'>${post.good}</td>
					<td class='bad'>${post.bad}</td>
					<td class='createdAt'>${post.createdAt}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="search">
		<label>
			<select id="searchType">
				<option value="all">전체 보기</option>
				<option value="postId">글 번호</option>
				<option value="category">카테고리</option>
				<option value="title">제목</option>
				<option value="author">작성자</option>
				<option value="content">내용</option>
				<option value="good">추천 수</option>
			</select>
		</label>
		<label>
			<input id="query" type="text" placeholder="검색어">
		</label>
		<input id="searchButton" type="submit" value="검색"/>
	</div>
	
	<div id="left">
		<a id="prev" href="">이전</a>
		<a id="next" href="">다음</a>
	</div>
	<div id="right">
		<a href="/DZBoard/board/create">글쓰기</a>
	</div>
</body>
</html>