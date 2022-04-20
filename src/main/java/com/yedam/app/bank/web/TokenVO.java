package com.yedam.app.bank.web;

import lombok.Data;

@Data
public class TokenVO {
	
	private String access_token;
	private String token_type;
	private String expires_in;
	private String refresh_token;
	private String scope;
	private String user_seq_no;
}
