<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<script type="text/javascript" src="memberListView.js"></script>
<title>관리자 - 회원 검색</title>
</head>
<body>
	<form>
		<label>
			<select id="searchType">
				<option value="all">검색조건</option>
				<option value="id" class="str">아이디</option>
				<option value="name" class="str">이름</option>
				<option value="email" class="str">이메일</option>
				<option value="phone" class="str">연락처</option>
				<option value="createdAt" class="date">회원 가입 일자</option>
				<option value="updatedAt" class="date">최근 활동 일자</option>
				<option value="authority" class="str">권한 레벨</option>
			</select>
		</label>
		<label>
			<input id="first" type="text"/>
		</label>
		<label>
			<input id="second" type="hidden"/>
		</label>
		<input type="submit" id="searchButton" value="검색">
	</form>
	<table>
		<thead>
			<tr>
				<th>아이디</th>
				<th>비밀번호</th>
				<th>이름</th>
				<th>이메일</th>
				<th>연락처</th>
				<th>회원 가입 일자</th>
				<th>최근 활동 일자</th>
				<th>권한 레벨</th>
				<th>수정</th>
				<th>삭제</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
	<footer>
		<button id="firstButton">처음</button>
		<button class='useCursor' data-value='-2'></button>
		<button class='useCursor' data-value='-1'></button>
		<button class='useCursor' data-value='0'></button>
		<button class='useCursor' data-value='1'></button>
		<button class='useCursor' data-value='2'></button>
		<button id="lastButton">마지막</button>
		
	</footer>
</body>
</html>