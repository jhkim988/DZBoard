<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>DZBoard 회원 정보 수정</title>
<script type="text/javascript" src="./js/updateMember.js"></script>
</head>
<body>
	<c:choose>
		<c:when test='${member != null}'>
			<form>
				<label>
					아이디: <input type="text" value="${member.id}" readonly/>
				</label>
				<br/>

				<label>
					비밀번호: <input id="pwd" type="password" value='${member.pwd}'/>
				</label>
				<br/>

				<label>
					이름: <input id="name" type="text" value='${member.name}'/>
				</label>
				<br/>

				<label>
					이메일: <input id="email" type="text" value='${member.email}'/>
				</label>
				<br/>	
				
				<label>
					연락처: <input id="phone" type="text" value='${member.phone}'/>
				</label>
				<br/>
				
				<label>
					회원 가입 일자: <input type="text" value='${member.createdAt}' readonly />
				</label>
				<br/>
				
				<label>
					최근 활동 일자: <input type="text" value='${member.updatedAt}' readonly />
				</label>
				<br/>
				
				<label>
					권한: <input type="text" value='${member.authority}' readonly />
				</label>
				<br/>
				<input id="updateButton" type="submit" value="수정"/>
			</form>
			<a href="/DZBoard/member/viewMember.jsp"><button>뒤로 가기</button></a>
		</c:when>
		<c:otherwise>
			<h1>권한 없음</h1>
		</c:otherwise>
	</c:choose>
</body>
</html>