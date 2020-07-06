<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>목록 보기</title>
</head>
<body>
	<h2 align="center">목록보기</h2>
	<table align="center" border="1" id="table">
		<tr>
			<th>코드</th>
			<th>카테고리</th>
			<th>제목</th>
		</tr>	
	</table>
</body>

<!-- jquery를 사용하기 위한 링크 설정 -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	//페이지 번호 변수
	var pageno = 1;
	
	//ajax 요청을 해서 table에 데이터를 출력해주는 함수
	function adddata(){
		$.ajax({
			url:"list",
			dataType:"json",
			data:{"no":pageno},
			success:function(data){
				//더보기 버튼 삭제
				$('#add').remove();
				//배열을 순회해서 하나의 객체를 한 줄로 출력
				$.each(data.ar, function(index, item){
					disp = "<tr><td>" + item.code + "</td>";
					disp += "<td>" + item.category + "</td>";
					disp += "<td>" + item.title + "</td></tr>";
					$('#table').html($('#table').html() + disp);
				});
				//더보기 버튼 만들기
				//현재 페이지가 종료 페이지보다 작을 때 만 생성
				if(data.pageno < data.endpage){
					//페이지 번호 하나 올리기
					pageno = pageno + 1;
					
					disp = "<tr id='add'>" + "<td colspan='3' align='center'>" + "더보기"	+ "</td></tr>";
					$("#table").html($("#table").html() + disp);
					//id 가 add 객체를 click 하면 adddata 라는 함수를 호출
					document.getElementById("add")
					.addEventListener("click", adddata);
				}
			}
		});
	};
	
	//태그를 전부를 읽고 난 후 수행
	window.addEventListener("load", function(e){
		adddata();
	})
</script>


</html>