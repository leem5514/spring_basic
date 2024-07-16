package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

// @SpringBootApplication -> basic과 동등 혹은 그 이하 컴포넌트들을 싱글턴 객체로 만들어 준다
// 해당 어노테이션을 통해서 컴포넌트 스캐닝을 진행한다
@SpringBootApplication
//주로 web 서블릿 기반의 구성요소를 스캔하고, 자동으로 등록하는 긴으
// 예로 )@webServlet, @webFilter , @WebListener 등.
@ServletComponentScan
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
