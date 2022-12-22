<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="/DZBoard/resources/admin/css/memberListView.css"/>
<script type="text/javascript" src="/DZBoard/resources/admin/js/memberListView.js"></script>
<title>관리자 - 회원 검색</title>
</head>
<body>
	<form>
		<label>
			<select id="searchType">
				<option value="allMember">검색조건</option>
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
			<tr style="display:none">
				<td class='id'></td>
				<td class='pwd'></td>
				<td class='name'></td>
				<td class='email'></td>
				<td class='phone'></td>
				<td class='createdAt'></td>
				<td class='updatedAt'></td>
				<td class='authority'></td>
				<td class='updateFormButton'><a href="#">수정</a></td>
				<td class='deleteButton'><a href="#">삭제</a></td>
			</tr>
		</tbody>
	</table>
	<button id="more" data-request=''>더 보기</button>
</body>
</html>