<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/DZBoard/board/createPost">
	<label>
		<input type="text" name="title" placeholder="제목"/>
		<br/>
	</label>
	<label>
		<input type="text" name="category" placeholder="카테고리"/>
		<br/>
	</label>
	<label>
		<textarea placeholder="내용" name="content"></textarea>
		<br/>
	</label>
	<input type="submit" value="등록"/>
	<input type="reset" value="취소"/>
</form>
<a href="/DZBoard/board">뒤로가기</a>
</body>
</html>