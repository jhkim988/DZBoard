<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board - 관리자 페이지 - URL 권한 수정</title>
<script type="text/javascript" src="/DZBoard/resources/member/js/updateUrlAuthority.js"></script>
</head>
<body>
	<table>
		<thead>
			<tr>
				<th>URL</th>
				<th>설명</th>
				<th>권한</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${urlAuth.url}</td>
				<td>${urlAuth.note}</td>
				<td>${urlAuth.authority}</td>
			</tr>
			<tr>
				<td><input id="url" type="text" placeholder="url" value="${urlAuth.url}"/></td>
				<td><input id="note" type="text" placeholder="설명" value="${urlAuth.note}"/></td>
				<td><input id="authority" type="number" placeholder="0~99" value="${urlAuth.authority}"/></td>
				<td><button id="updateButton">수정</button></td>
			</tr>
		</tbody>
	</table>
</body>
</html>