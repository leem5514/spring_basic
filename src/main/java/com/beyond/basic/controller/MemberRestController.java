package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
//import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// restController 의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
@Api(tags = "회원관리서비스")
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/member/text")
    public String memberText() {
        return "ok";
    }

    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member/list")
    public List<MemberResDto> memberList() {
        List<MemberResDto> memberList = memberService.memberList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", memberList);

        return (List<MemberResDto>) new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
    }
    @GetMapping("/member/{id}")
    public String memberDetail(@PathVariable Long id, Model model) {
//        List<MemberResDto> memberDetail = memberService.memberList();
//        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", memberService.memberDetail(id));
//        CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.CREATED, "error code" , memberDetail);

        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/memberList";
    }
    @PostMapping("/member/create")
    public ResponseEntity<Object> memberCreatePost(@RequestBody MemberReqDto dto) {
        try {

            memberService.memberCreate(dto);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", null);

            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
            return new ResponseEntity<>(commonErrorDto, HttpStatus.CREATED);
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
