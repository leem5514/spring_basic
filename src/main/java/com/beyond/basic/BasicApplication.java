package com.beyond.basic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication -> basic과 동등 혹은 그 이하 컴포넌트들을 싱글턴 객체로 만들어 준다
// 해당 어노테이션을 통해서 컴포넌트 스캐닝을 진행한다
@SpringBootApplication
public class BasicApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicApplication.class, args);
	}

}
