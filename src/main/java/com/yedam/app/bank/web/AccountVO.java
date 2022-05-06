package com.yedam.app.bank.web;

import lombok.Data;

@Data
public class AccountVO {
	private String account_holder_name;
	private String bank_name;
	private String account_num_masked;
	private String account_type;
	private String fintech_use_num;
	private String account_alias;
	
	
}
