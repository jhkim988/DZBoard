<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>관리자 - 회원 정보 수정</title>
<script type="text/javascript" src="/DZBoard/resources/admin/js/updateMember.js"></script>
</head>
<body>
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
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${ manage_member.id }</td>
				<td>${ manage_member.pwd }</td>
				<td>${ manage_member.name }</td>
				<td>${ manage_member.email }</td>
				<td>${ manage_member.phone }</td>
				<td>${ manage_member.createdAt }</td>
				<td>${ manage_member.updatedAt }</td>
				<td>${ manage_member.authority }</td>
			</tr>
			<tr>
				<td>${ manage_member.id }</td>
				<td><input type="text" id="pwd" value='${manage_member.pwd}'/></td>
				<td><input type="text" id="name" value='${manage_member.name}'/></td>
				<td><input type="text" id="email" value='${manage_member.email}'/></td>
				<td><input type="text" id="phone" value='${manage_member.phone}'/></td>
				<td>${ manage_member.createdAt }</td>
				<td>${ manage_member.updatedAt }</td>
				<td><input type="number" id="authority" value='${manage_member.authority}'/></td>
			</tr>
		</tbody>
	</table>
	<button id="updateButton">수정</button>
</body>
</html>