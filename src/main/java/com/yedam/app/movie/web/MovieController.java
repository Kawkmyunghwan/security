package com.yedam.app.movie.web;


import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MovieController {
	
	@RequestMapping("/firstMovieNm")
	@ResponseBody
	public ResponseEntity<String> firstMovieNm(HttpServletResponse response,
		@RequestParam(required = false, defaultValue = "20220218") String targetDt) throws UnsupportedEncodingException {
		response.setContentType("text/html; charset=UTF-8");
		String nm = MovieApi.firstMovie(targetDt);
		return new ResponseEntity<String>(nm, null, HttpStatus.OK); //한글인코딩
	}
}
