<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>  
<script src="https://code.jquery.com/jquery-3.6.0.js" integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk=" crossorigin="anonymous"></script>
</head>
<body>
<h3 id="account">계좌리스트</h3>
<div id="list"></div>

<input id="balance" type="text">

<script>
	let url = "accountList";
	$.ajax(url)
	.done(function(datas){
		console.log(datas);
		for(let i=0; i<datas.length; i++){
			$('#list').append($('<h3>').html(datas[i].account_holder_name))
			$('#list').append($('<h3>').html(datas[i].bank_name));
			$('#list').append($('<h3>').html(datas[i].account_num_masked))
			$('#list').append($('<button id="btn">잔액조회</button>'))
		}
		
		$('#list').on('click', '#btn', function(){
			$.ajax({
				url : "getBalance"
			}).done(function(datas){
				console.log(datas);
				$('#list').append($('#balance').val(datas))
			})
		})
	})
</script>
</body>
</html>