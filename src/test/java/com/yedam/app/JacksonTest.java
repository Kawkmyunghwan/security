package com.yedam.app;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yedam.app.notice.vo.NoticeVO;

public class JacksonTest {
	static ObjectMapper om;

	@BeforeClass
	public static void init() {
		om = new ObjectMapper();
	}
	
	@Test
	public void readTreeTest2() throws MalformedURLException, IOException {
		//API 요청
		String infoUrl = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json?key=f5eef3421c602c6cb7ea224104795888&movieCd=20112207";
		RestTemplate restTemplate = new RestTemplate();
		JsonNode json = restTemplate.getForObject(infoUrl, JsonNode.class);
		
		//결과 조회
		json = json.get("movieInfoResult")
			       .get("movieInfo");
		//VO로 변환
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MovieInfoVO mvInfo = om.treeToValue(json, MovieInfoVO.class);
		
		//첫번째 감독 출력
		System.out.println(mvInfo.getDirectors().get(0).get("peopleNm"));
		
	}
	
	@Test
	public void readTreeTest() throws MalformedURLException, IOException {
		String boxUrl = "http://kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json?key=f5eef3421c602c6cb7ea224104795888&targetDt=20120101";
		RestTemplate restTemplate = new RestTemplate();
		JsonNode json = restTemplate.getForObject(boxUrl, JsonNode.class);
	
		JsonNode mvList = json.get("boxOfficeResult")
					      .get("dailyBoxOfficeList");
		om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MovieVO[] arr = om.treeToValue(mvList, MovieVO[].class);
		
		List<MovieVO> list = Arrays.asList(arr);
		System.out.println(list.get(0).getMovieNm());
	}

	@Test
	public void readTest() throws JsonMappingException, JsonProcessingException {
		String str = "{\"id\":0,\"title\":\"제목\",\"content\":\"내용\",\"wdate\":null,\"hit\":0}";
		NoticeVO vo = om.readValue(str, NoticeVO.class);
		assertEquals(vo.getTitle(), "제목");
		//System.out.println(vo.getTitle());
	}

	@Test
	public void writeTest() {
		NoticeVO vo = NoticeVO.builder().title("제목").content("내용").build();
		try {
			String str = om.writeValueAsString(vo);
			System.out.println(str);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}
	
	
}
