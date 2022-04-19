package com.yedam.app;

import java.beans.Encoder;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yedam.app.member.service.MemberService;
import com.yedam.app.member.service.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:/spring/*-context.xml")

public class MemberServiceClient {
	
	@Autowired MemberService memberService;
	@Test
	public void insertMember() {
		MemberVO vo = MemberVO.builder().id("test1")
										.name("test1")
										.password(encoder.encode("1111"))
										.author("")
										.build();
		memberService.insertMember(null);
	}
}
