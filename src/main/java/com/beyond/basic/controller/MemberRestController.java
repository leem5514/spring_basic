package com.beyond.basic.controller;

import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.domain.MemberUpdateDto;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// restController 의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
public class MemberRestController {

    private final MemberService memberService;


    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/list")
    public List<MemberResDto> memberList() {
        List<MemberResDto> memberList = memberService.memberList();
        return memberList;
    }
    @GetMapping("/member/detail/{id}")
    public MemberDetailResDto memberDetail(@PathVariable Long id) {
        return memberService.memberDetail(id);
    }
    @PostMapping("/member/create")
    public String memberCreatePost(@RequestBody MemberReqDto dto) {
        try {
            memberService.memberCreate(dto);
            return "ok";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "error!";
        }
    }
    // 수정은 2가지 요청방식 : PUT, PATCH 요청
    // PATCH 요청은 부분수정, PUT 요청은 덮어쓰기

    @PatchMapping("/member/pw/update")
    public String memberList(@RequestBody MemberUpdateDto dto ) {
        memberService.pwUpdate(dto);
        return "ok";
    }

    public void pwUpdate(MemberUpdateDto dto) {

    }

    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id) {
        return "ok";
    }

    // 화면 Return -> MVC 패턴 (JSP 또는 타임리프) -> 데이터를 return 해서 : rest -api 방식

}
