<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터 수정</title>
</head>
<body>
	<!-- action을 생략하면 이전과 동일한 요청 -->
	<h3>데이터 수정</h3>
	<form method="post">
		코드<input type="text" value="${item.code}"
		name="code" id="code" readonly="readonly" /><br/>
		카테고리<input type="text" value="${item.category}"
		name="category" id="category" readonly="readonly" /><br/>
		이름<input type="text" value="${item.title}"
		name="title" id="title" /><br/>
		설명<textarea name="description" id="description"rows="10" cols="30">${item.description}</textarea><br/>
		<input type="submit" value="수정" />
		<input type="button" value="메인" id="mainbtn"/>
		<input type="button" value="목록" id="listbtn"/>
		
	</form>
</body>
<script>
	document.getElementById("mainbtn")
		.addEventListener("click", function(event){
		location.href=
			"${pageContext.request.contextPath}/";
	});
	document.getElementById("listbtn")
	.addEventListener("click", function(event){
	location.href=
		"${pageContext.request.contextPath}/item/list";
	});
</script>
</html>


