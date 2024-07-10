package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MemberController {
    //회원 목록 조회
    @GetMapping("/member/list")
    public String memberList() {

        return "member/memberList";
    }
    // 회원 상세 조회
    // url : member/1, member/2
    @GetMapping("/member/{id}")
    // int 또는 long 받을 경우 spring에서 형변환 적용(string -> long, int)
    public String memberDetail(@PathVariable Long id) {
        return "member/memberDetail";
    }

    // 회원가입 주고
    // url : member/create
    //회원데이터를 받는다.
    // url : member /create
    // name, email, password
    @GetMapping("/member/create")
    public String memberCreate() {
        return "member/memberCreate";
    }

    @PostMapping("/member/create")
    @ResponseBody
    public String memberCreate(@ModelAttribute Member member) {
        member.setName(member.getName());
        member.setEmail(member.getEmail());
        member.setPassword(member.getPassword());
        System.out.println(member);
        return "ok";
    }

}
