<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Douzone Board - 게시글 수정</title>
<link rel="stylesheet" href="/DZBoard/board/css/postform.css"/>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/ckeditor.js"></script>
<script src="https://cdn.ckeditor.com/ckeditor5/35.3.2/classic/translations/ko.js"></script>
<script type="text/javascript" src="/DZBoard/board/js/update.js"></script>
</head>
<body>
<form>
	<input type="hidden" id="id" value="${oldPost.id}"/>
	<label>
		<input type="text" id="title" placeholder="글제목" value="${oldPost.title}">
	</label>	
	<label>
		<select id="category">
			<c:forEach var='category' items='${categoryList}'>
				<option value="${category.textContent}">${category.textContent}</option>
			</c:forEach>	
		</select>
		<br/>
	</label>
	<label>내용</label>
	<textarea name="content" id="editor">${oldPost.content}</textarea>
	
	<input id="send" type="submit" value="전송">
</form>
<a href="/DZBoard/board">뒤로가기</a>
</body>
<script>
	let editor;
	ClassicEditor
	    .create( document.querySelector( '#editor' ), {language : "ko"} )
	    .then( newEditor => {
	        editor = newEditor;
	    } )
	    .catch( error => {
	        console.error( error );
	});
</script>
</html>

<!-- 

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

 -->