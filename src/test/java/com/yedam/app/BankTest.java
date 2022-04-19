package com.yedam.app;

import java.util.List;

import org.junit.Test;

import com.yedam.app.bank.web.AccountVO;
import com.yedam.app.bank.web.BankApi;
import com.yedam.app.bank.web.BankVO;

public class BankTest {

	@Test
	public void getBalance() {
		BankVO vo = new BankVO();
		long balance = BankApi.getBalanceInfo(vo);
		System.out.println(balance);
				
	}
	
	@Test
	public void getAccountList() {
		BankVO vo = new BankVO();
		List<AccountVO> list = BankApi.getAccountList(vo);
		System.out.println(list);
				
	}
}
