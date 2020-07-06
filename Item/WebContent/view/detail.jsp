<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상세보기</title>
</head>
<body>
	<table border="1" align="center">
		<tr>
			<td>제목</td>
			<td>${item.title}</td>
		</tr>
		<tr>
			<td>내용</td>
			<td>${item.description}</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<!-- 수정과 삭제는 기본키를 넘겨받아야 합니다. -->
				<!-- 절대 경로로 링크를 생성 -->
				<a href="${pageContext.request.contextPath}/item/update/${item.code}">수정</a>&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath}/item/delete/${item.code}">삭제</a>&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath}/item/list">목록</a>
			</td>
		</tr>	
				
	</table>
</body>
</html>