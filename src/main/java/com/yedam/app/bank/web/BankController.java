package com.yedam.app.bank.web;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankController {

	@GetMapping("/accountList")
	public List<AccountVO> accountList(BankVO vo) {
		return BankApi.getAccountList(vo);
	}

	@GetMapping("/getBalance")
	public long getBalanceInfo(BankVO vo) {
		return BankApi.getBalanceInfo(vo);
	}

	@GetMapping("/getTransaction")
	public List<TransActionVO> getTransaction(BankVO vo) {
		return BankApi.getTransaction(vo);
	}
	
	@GetMapping("/changeName")
	public Map changeName(BankVO vo) {
		return BankApi.changeName(vo);
	}
	
	@GetMapping("/getRealName")
	public Map getRealName(BankVO vo) {
		return BankApi.getRealName(vo);
	}

	
}
