<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board 글 작성</title>
<link rel="stylesheet" href="/DZBoard/board/css/postform.css"/>
<script type="text/javascript" src="/DZBoard/board/js/create.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/translations/ko.js"></script>
</head>
<body>
<form id="createPostForm" method="post">
	<label>
		<input type="text" id="title" name="title" placeholder="글제목">
	</label>	
	<label>
		<select id="category" name="category">
			<c:forEach var='category' items='${categoryList}'>
				<option value="${category.textContent}">${category.textContent}</option>
			</c:forEach>	
		</select>
		<br/>
	</label>
	<label>내용</label>
	<textarea id="editor" name="content"></textarea>
	<div id="fileUploads">
	<label>
		<input type="file" id="file" class="file" name="file" data-prev=''/>
		<button id="fileInputCancel" class="fileInputCancel">X</button>
	</label>
	</div>
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