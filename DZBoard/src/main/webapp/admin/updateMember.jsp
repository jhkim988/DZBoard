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
	<button id="updateButton">����</button>
</body>
</html>