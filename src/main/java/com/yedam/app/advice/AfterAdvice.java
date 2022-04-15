package com.yedam.app.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

@Service   //컨테이너에 빈등록
@Aspect    //advice + aspect
public class AfterAdvice {
	
	@AfterReturning( pointcut = "LogAdvice.allpointcut()", returning = "returnObj")
	public void afterLog(JoinPoint jp, Object returnObj){
		String method = jp.getSignature().getName();
		String result = returnObj != null ? returnObj.toString() : "";

		System.out.println("[사후처리] 로직 수행 후 동작" +
		                    method + " : " + 
		                    result );
	}
}
