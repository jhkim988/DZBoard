<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>관리자 - 회원 정보 수정</title>
<script type="text/javascript" src="./updateMember.js"></script>
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
				<td>${ member.id }</td>
				<td>${ member.pwd }</td>
				<td>${ member.name }</td>
				<td>${ member.email }</td>
				<td>${ member.phone }</td>
				<td>${ member.createdAt }</td>
				<td>${ member.updatedAt }</td>
				<td>${ member.authority }</td>
			</tr>
			<tr>
				<td>${ member.id }</td>
				<td><input type="text" id="pwd" value='${member.pwd}'/></td>
				<td><input type="text" id="name" value='${member.name}'/></td>
				<td><input type="text" id="email" value='${member.email}'/></td>
				<td><input type="text" id="phone" value='${member.phone}'/></td>
				<td>${ member.createdAt }</td>
				<td>${ member.updatedAt }</td>
				<td><input type="number" id="authority" value='${member.authority}'/></td>
			</tr>
		</tbody>
	</table>
	<button id="updateButton">수정</button>
</body>
</html>