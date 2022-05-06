package com.yedam.app.bank.web;

import lombok.Data;

@Data
public class DepositVO {

	private String dps_bank_name;
	private String dps_account_holder_name;
	private String bank_tran_date;
	private String bank_name;
	private String tran_amt;
	private String wd_limit_remain_amt;
}
