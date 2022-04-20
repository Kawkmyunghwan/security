package com.yedam.app;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class MovieInfoVO {
	private List<Map<String, String>> actors;
	private List<Map<String, String>> companys;
	private List<Map<String, String>> directors;
}
