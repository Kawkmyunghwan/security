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
<h3>계좌리스트</h3>
<div id="list">
	<div class="acc" data-usenum="12323232"><span>대구은행</span><span>1111222***</span></div>
</div>

<div id="div">
	<h3>실명인증페이지</h3>
	계좌번호 <input type="text" id="accountNum">
	주민번호앞자리 <input type="text" id="frontNum">
	<button id="btn">실명인증하기</button>
</div>
<div id="result"></div>

<script>
	let url = "accountList";
	$.ajax(url)
	.done(function(result){
		console.log(result);
		for(bank of result){
			$("<div>").addClass("acc")
					  .data("usenum", bank.fintech_use_num)
					  .append( $("<span>").html(bank.bank_name) )
					  .append( $("<span>").html(bank.account_num_masked) )
					  .append( $("<input>").val(bank.account_alias) )
					  .append( $("<button>").html("변경").addClass("btnTrans") )					  
					  .appendTo("#list")
		}
		console.log($('<input>').val());
		//잔액조회
		$('#list').on('click', 'span', function(){
			var num = $(this).closest(".acc").data("usenum");
			$.ajax({
				url : "getBalance",
				data : {fintechUseNum : num}
			}).done(function(datas){
				console.log(datas);
				$("#result").html(datas);
			})
		})
		
		$("#list").on("click", ".btnTrans", function(){
			var num = $(this).closest(".acc").data("usenum");
			var name = $(this).closest(".acc").find('input').val();
			console.log(name);
			var url = "changeName";
			$.ajax({
				url : url,
				data : {fintechUseNum : num,
						changeName : name}
			}).done(function(response){
				console.log(response);
				
			})
		})
		
		$("#div").on("click", "#btn", function(){
			var num = $('#accountNum').val();
			var frontNum = $('#frontNum').val();
			console.log(num);
			console.log(frontNum);
			var url = "getRealName";
			$.ajax({
				url : url,
				data : {account_num : num,
					    account_holder_info : frontNum},
				contentType: "application/x-www-form-urlencoded; charset=UTF-8"
			}).done(function(response){
				console.log(response.account_holder_name);
				$('<p>').html(response.account_holder_name).appendTo('#result');
			})
		})
	})
</script>
</body>
</html>