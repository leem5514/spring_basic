package com.beyond.basic.controller;

import com.beyond.basic.domain.CommonResDto;
import com.beyond.basic.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/response/entity")
public class ResponseEntityController {
    //CASE 1. @ResponseStatus 어노테이션 방식
    @GetMapping("/annotation1")
    @ResponseStatus(HttpStatus.OK)
    public String annotation1() {
        return "ok";
    }
    @GetMapping("/annotation2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member annotation2() {
        //(IF) 객체 생성후 DB 저장 성공
        Member member = new Member("hongildong", "hong@naver.com", "1234");
        return member;
    }

    //CASE 2. 메서드 체이닝 방식 : ResponseEntity의 클래스메서드 사용
    @GetMapping("/chaining1")
    public ResponseEntity<Member> chaining1() {
        Member member = new Member("hongildong", "hong@naver.com", "1234");
        return ResponseEntity.ok(member);//200
    }
    @GetMapping("/chaining2")
    public ResponseEntity<Member> chaining2() {
        Member member = new Member("hongildong", "hong@naver.com", "1234");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);//201
    }
    @GetMapping("/chaining3")
    public ResponseEntity<Member> chaining3() {
        return ResponseEntity.notFound().build();//404
    }

    // CASE 3. ResponseEntity객체를 직접 custom하여 생성하는 방식
    @GetMapping("/custom1")
    public ResponseEntity<Member> custom1() {
        Member member = new Member("hongildong", "hong@naver.com", "1234");
        return new ResponseEntity<>(member, HttpStatus.CREATED); //201

    }

    @GetMapping("/custom2")
    public ResponseEntity<CommonResDto> custom2() {
        Member member = new Member("hongildong", "hong@naver.com", "1234");
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", member);
        return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }




}
