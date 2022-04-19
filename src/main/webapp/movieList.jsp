<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.js"></script>
</head>
<body>
<h3>서버를 통해서 데이터 넘겨주기</h3>
<div id="list"></div>
<script>
let url = "firstMovieNm";
	$.ajax({url:url})
	.done(function(datas){
		$('#list').html(datas);
	})
</script>
</body>
</html>