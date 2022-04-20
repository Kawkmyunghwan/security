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
					  .append( $("<input>").val(bank.별칭) )
					  .append( $("<button>").html("거래내역").addClass("btnTrans") )
					  .appendTo("#list")
		}
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
			var url = "getTransaction";
			$.ajax({
				url : url,
				data : {fintechUseNum : num}
			}).done(function(response){
				console.log(response);
				for(data of response){
					$('<p>').html(data.tran_date + "  " + data.after_balance_amt + "  " + data.tran_amt).appendTo('#result')
				}
			})
		})
	})
</script>
</body>
</html>