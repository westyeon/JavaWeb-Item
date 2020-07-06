<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>데이터 입력 화면</title>
</head>
<body>
	<h3>아이템 입력</h3>
	<!-- title, category, description을 입력 -->
	<form method="post" id="itemform">
		카테고리
		<select name="category" id="category">
			<option value="language">언어</option>
			<option value="IDE">통합개발환경</option>
			<option value="Database">데이터베이스</option>
			<option value="etc">기타</option>
		</select><br />
		제목<input type="text" name="title" id="title"/>
		<br/>
		설명<textarea rows="10" cols="30" 
		name="description" id="description"></textarea>
		<input type="submit" value="전송" />
		<input type="button" value="메인" id="mainbtn"/>
	</form>
</body>
	<script>
		document.getElementById("mainbtn")
			.addEventListener("click", function(){
				location.href = "../";
			})
	</script>
</html>








