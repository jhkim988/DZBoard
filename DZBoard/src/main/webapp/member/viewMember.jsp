<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
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
					비밀번호: <input type="password" value='${member.pwd}' readonly/>
				</label>
				<br/>

				<label>
					이름: <input type="text" value='${member.name}' readonly/>
				</label>
				<br/>

				<label>
					이메일: <input type="text" value='${member.email}' readonly/>
				</label>
				<br/>	
				
				<label>
					연락처: <input type="text" value='${member.phone}' readonly/>
				</label>
				<br/>
				
				<label>
					회원 가입 일자: <input type="text" value='${member.createdAt}'readonly />
				</label>
				<br/>
				
				<label>
					최근 활동 일자: <input type="text" value='${member.updatedAt}' readonly />
				</label>
				<br/>
				
				<label>
					권한: <input type="text" value='${member.authority}' readonly/>
				</label>
				<br/>
			</form>
			<a href="/DZBoard"><button>뒤로 가기</button></a>
	</c:when>
	<c:otherwise>
	
	</c:otherwise>
	</c:choose>
</body>
</html>