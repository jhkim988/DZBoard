<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="/DZBoard/board/updatePost">
	<label>
		<input type="hidden" name="id" value="${id}"/>
		글 번호: ${post.id}
		<br/>
	</label>
	
	<label>
		<input type="text" name="title" value="${post.title}" placeholder="제목"/>
		<br/>
	</label>
	<label>
		<input type="text" name="category" value="${post.category}" placeholder="카테고리"/>
		<br/>
	</label>
	<label>
		<input type="text" name="content" value="${post.content}" placeholder="내용"/>
		<br/>
	</label>
	<input type="submit" value="수정"/>
	<input type="reset" value="취소"/>
</form>
<a href="/DZBoard/board">뒤로가기</a>
</body>
</html>