<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript" src="./updateMember.js"></script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>���̵�</th>
				<th>��й�ȣ</th>
				<th>�̸�</th>
				<th>�̸���</th>
				<th>����ó</th>
				<th>ȸ�� ���� ����</th>
				<th>�ֱ� Ȱ�� ����</th>
				<th>���� ����</th>
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
	<button id="updateButton">����</button>
</body>
</html>