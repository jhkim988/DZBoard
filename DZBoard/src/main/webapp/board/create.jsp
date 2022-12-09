<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/translations/ko.js"></script>
<script type="text/javascript" src="create.js"></script>
</head>
<body>
<form method="post">
	<label>
		<input type="text" id="title" placeholder="글제목">
	</label>	
	<label>
		<select id="category">
			<c:forEach var='category' items='${categoryList}'>
				<option value="${category.value}">${category.textContent}</option>
			</c:forEach>	
		</select>
		<br/>
	</label>
	<label>내용</label>
	<textarea name="content" id="editor"></textarea>
	
	<input id="send" type="submit" value="전송">
</form>
<a href="/DZBoard/board">뒤로가기</a>
</body>
<script>
	ClassicEditor
	    .create( document.querySelector( '#editor' ), {language : "ko"} )
	    .catch( error => {
	        console.error( error );
	});
</script>
</html>