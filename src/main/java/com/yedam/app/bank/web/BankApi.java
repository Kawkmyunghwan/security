package com.yedam.app.bank.web;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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
	
	static String oob_token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJNMjAyMjAwNzI4Iiwic2NvcGUiOlsib29iIl0sImlzcyI6Imh0dHBzOi8vd3d3Lm9wZW5iYW5raW5nLm9yLmtyIiwiZXhwIjoxNjU4MTkwMjE2LCJqdGkiOiJlNjQ2MzZkZi0wYmY5LTQ1NGYtOGZhNy1lMDIxZGRjMDEzMDAifQ.PmEYudlHcKEN67Vf1iiYhsqtDPZnRpqUhhMhABg6JdY";
	
	public static Map getRealName(BankVO vo) {
		String reqUrl = "https://testapi.openbanking.or.kr/v2.0/inquiry/real_name";
		Map<String, String> param = new HashMap<>();
		param.put("bank_tran_id", "M202200728U" + getSequence());
		param.put("bank_code_std", "097");
		param.put("account_num", vo.getAccount_num());
		param.put("account_holder_info_type", " ");
		param.put("account_holder_info", vo.getAccount_holder_info());
		param.put("tran_dtime", getData());
		//
		String jsonValue = "";
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			jsonValue = objectMapper.writeValueAsString(param);
			System.out.println(jsonValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		headers.set("Authorization", "Bearer " + oob_token);
		
		HttpEntity<String> request = new HttpEntity<String>(jsonValue, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		Map response = restTemplate.postForObject(reqUrl, request, Map.class);
		System.out.println(response);
		String realName = (String) response.get("account_holder_name");
		
		return response;
	}
	
	
	
	
	public static Map changeName(BankVO vo) {
		String reqURL = "https://testapi.openbanking.or.kr/v2.0/account/update_info";
		
		Map<String, String> param = new HashMap<>();
		param.put("fintech_use_num", vo.getFintechUseNum());
		param.put("account_alias", vo.getChangeName());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonValue = "";
		try {
			jsonValue = objectMapper.writeValueAsString(param);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-Type", "application/json; charset=UTF-8");
		headers.set("Authorization", "Bearer " + vo.getAccessToken());
		
		HttpEntity<String> request = new HttpEntity<String>(jsonValue, headers);

		RestTemplate restTemplate = new RestTemplate();
		Map response = restTemplate.postForObject(reqURL, request, Map.class);
		System.out.println(response);
		
		return response;
	}
	
	public static List<DepositVO> getDepositResult(BankVO vo){
		String reqURL = "https://testapi.openbanking.or.kr/v2.0/transfer/withdraw/fin_num";
		
		Map<String, String> param = new HashMap<>();
		param.put("bank_tran_id", "M202200728U" + getSequence());
		param.put("cntr_account_type", "N");
		param.put("cntr_account_num", "1234567890123456");
		param.put("dps_print_content", );
		param.put("fintech_use_num", vo.getFintechUseNum());
		param.put("tran_amt", );
		param.put("tran_dtime", );
		param.put("req_client_name", );
		param.put("req_client_num", );
		param.put("transfer_purpose", );
		return null;
	}
	
	
	
	
	public static List<TransActionVO> getTransaction(BankVO vo) {
		String reqUrl = "https://testapi.openbanking.or.kr/v2.0/account/transaction_list/fin_num";
		
		//파라미터 setting
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("bank_tran_id", "M202200728U" + getSequence());
		map.add("fintech_use_num", vo.getFintechUseNum());
		map.add("inquiry_type", "A");
		map.add("inquiry_base", "D");
		map.add("from_date", "20220320");
		map.add("to_date", "20220420");
		map.add("sort_order", "D");
		map.add("tran_dtime", getData());
		//MultiValueMap => queryString으로 변환
		
		URI uri = UriComponentsBuilder.fromUriString(reqUrl)
				.queryParams(map).build().encode().toUri();
		
		//header setting
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + vo.getAccessToken());
		
		//request 생성(파라미터와 헤더를 지정해서)
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		//RestTemplate을 이용하여 request를 보내고 response받고 json 결과를 객체로 파싱
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.exchange(uri, 
													 HttpMethod.GET, 
													 request, 
												     String.class);
		
		ArrayList<TransActionVO> list = null;
		ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		try {
			JsonNode json = objectMapper.readTree(response.getBody());
			json.get("res_list");
			String str = objectMapper.writeValueAsString(json.get("res_list"));
			TransActionVO[] arr = objectMapper.readValue(str, TransActionVO[].class);
			
			list = new ArrayList<>(Arrays.asList(arr));
			System.out.println(list);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	
	
	
	
	/* public static TokenVO getToken(String authCode) {
		String reqUrl = "https://testapi.openbanking.or.kr/oauth/2.0/token";
		
		MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
		map.add("code", "");
		map.add("client_id", "14c73348-c1ec-4690-8fcf-07da64eae6df");
		map.add("client_secret", "6e8c720a-c630-4ece-91d2-e2b610b6860a");
		map.add("redirect_uri", "");
		map.add("grant_type", "authorization_code");
		
		//header setting
				HttpHeaders headers = new HttpHeaders();
				headers.set("application/x-www-form-urlencoded;", "charset=UTF-8");
		
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
