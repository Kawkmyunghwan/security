package com.yedam.app.bank.web;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BankApi {
	
	public static String getData() {
		Date d = new Date();
		SimpleDateFormat time = new SimpleDateFormat("yyyyMMddhhmmss");
		String result = "";
		result = time.format(d);
		return result;
	}
	
	public static String getSequence() {
		long t = System.nanoTime();
		String result = String.valueOf(t);
		result = result.substring(result.length()-9);
		return result;
	}
	
	public static long getBalanceInfo(BankVO vo) {
				
		long balance = 0;
		String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/balance/fin_num";

		String param = "bank_tran_id=M202200728U" + getSequence();
		param += "&tran_dtime=" + getData();
		param += "&fintech_use_num=" + vo.getFintechUseNum();

		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", "Bearer " + vo.getAccessToken());

		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(null, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(reqURL + "?" + param, 
															HttpMethod.GET, 
															request, 
															Map.class);
		Map map = response.getBody();
		balance = Long.valueOf((String)map.get("balance_amt"));
		return balance;
	}
	
	
	
	
	
	public static List<AccountVO> getAccountList(BankVO vo) {
		
		String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/list";

		String param = "user_seq_no=1101005859";
		param += "&include_cancel_yn=" + "Y";
		param += "&sort_order=" + "D";

		HttpHeaders headers = new HttpHeaders();

		headers.set("Authorization", "Bearer " + vo.getAccessToken());

		HttpEntity<MultiValueMap<String, String>> request = 
				new HttpEntity<MultiValueMap<String, String>>(null, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(reqURL + "?" + param, 
															HttpMethod.GET, 
															request, 
															String.class);
		ArrayList<AccountVO> list= null;
		ObjectMapper om = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			JsonNode json = om.readTree(response.getBody());
			json = json.get("res_list");
			String str = om.writeValueAsString(json);
			
			AccountVO[] arr = om.readValue(str, AccountVO[].class);
			System.out.println(arr);
			list = new ArrayList<>(Arrays.asList(arr));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return list;
	}
}
