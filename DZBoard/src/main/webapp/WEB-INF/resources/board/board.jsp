<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board - 게시판</title>
<link rel="stylesheet" href="/DZBoard/resources/board/css/board.css" />
<script type="text/javascript"
	src="/DZBoard/resources/board/js/board.js"></script>
</head>
<body>
	<h1>
		<a href="/DZBoard/index">Douzone Board</a>
	</h1>
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
		</tbody>
		<tbody id="mainBoard">
			<tr id="postRow" style="display: none">
				<td class='id'></td>
				<td class='category'></td>
				<td class='title'><a href=""></a></td>
				<td class='author'></td>
				<td class='viewcount'></td>
				<td class='good'></td>
				<td class='bad'></td>
				<td class='createdAt'></td>
			</tr>
		</tbody>
	</table>
	<div id="search">
		<label> <select id="searchType">
				<option value="all">전체 보기</option>
				<option value="postId">글 번호</option>
				<option value="category">카테고리</option>
				<option value="title">제목</option>
				<option value="author">작성자</option>
				<option value="content">내용</option>
				<option value="good">추천 수</option>
		</select>
		</label> <label> <input id="query" type="text" placeholder="검색어">
		</label> <input id="searchButton" type="submit" value="검색" />
	</div>
	<footer>
		<div id="left">
			<button id="firstPage">처음</button>
			<button id="prevSet">&lt;</button>
			<div id="pageButtons" style='display: inline-block'>
				<button data-page='1'>1</button>
				<button data-page='2'>2</button>
				<button data-page='3'>3</button>
				<button data-page='4'>4</button>
				<button data-page='5'>5</button>
				<button data-page='6'>6</button>
				<button data-page='7'>7</button>
				<button data-page='8'>8</button>
				<button data-page='9'>9</button>
				<button data-page='10'>10</button>
			</div>
			<button id="nextSet">&gt;</button>
			<button id="lastPage">마지막</button>
		</div>
		<div id="right">
			<a id="write" href="/DZBoard/board/create">글쓰기</a>
		</div>
	</footer>
</body>
</html>