package com.yedam.app;

import java.util.List;

import org.junit.Test;

import com.yedam.app.bank.web.AccountVO;
import com.yedam.app.bank.web.BankApi;
import com.yedam.app.bank.web.BankVO;
import com.yedam.app.bank.web.TransActionVO;

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
	
	@Test
	public void getRealName() {
		String str = BankApi.getRealName();
		System.out.println(str);
	}
	
	@Test
	public void getTransaction() {
		BankVO vo = new BankVO();
		List<TransActionVO> list = BankApi.getTransaction(vo);
		System.out.println(list);
	}
}
