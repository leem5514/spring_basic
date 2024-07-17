package com.beyond.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    // Docket : Swagger 구성의 핵심기능 클래스
    // Docket 을 리턴함으로서 싱글톤 객체로 생성
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 어떤 컨트롤러 또는 api을 swagger 문서에 포함시킬 지 선택
                .select()
                // 모든 RequestHandler(Controller)을 문서화 대상으로 선택한다는 설정
                .apis(RequestHandlerSelectors.any())
                // 특정 path만 문서화 대상으로 하겠다라는 설정
                .paths(PathSelectors.ant("/rest/**")) // *이 1개이면 직계, ** 2개면 자손까지 포함
                .build();
    }
}
