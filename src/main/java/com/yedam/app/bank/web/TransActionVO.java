package com.yedam.app.bank.web;

import lombok.Data;

@Data
public class TransActionVO {
	private String tran_date;
	private String after_balance_amt;
	private String tran_amt;
}
