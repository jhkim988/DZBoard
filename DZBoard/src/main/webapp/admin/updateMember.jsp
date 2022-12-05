<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
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
				<td>-</td>
				<td><input type="text"/></td>
				<td><input type="text"/></td>
				<td><input type="text"/></td>
				<td><input type="text"/></td>
				<td>-</td>
				<td>-</td>
				<td><input type="number"/></td>
			</tr>
		</tbody>
	</table>
	<button id="updateButton">수정</button>
</body>
</html>