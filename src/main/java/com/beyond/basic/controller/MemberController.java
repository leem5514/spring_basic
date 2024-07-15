package com.beyond.basic.controller;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller // 싱글톤
//@RequiredArgsConstructor // 의존성 주입방식 3 : 어노테이션 이용방식
public class MemberController {
    //의존성 주입(DI)
    // 방법 1 : 생성자 주입방식(가장 多 사용)
        // 장점 : 1) final을 통해서 상수로 사용가능 , 2) 다형성 구현 가능, 3) 순환참조방식
        // @Autowired
        // public MemberController(MemberService memberService) {
        //     this.memberService = memberService;
        // }
    // 방법 2 : 필드 주입 방식(Autowired만 사용)
        // @Autowired
        // private MemberService service;
    // 방법 3 : 어노테이션(RequiredArgs)을 이용하는 방식
    // 생성자가 많으면 많을 수록 생성자의 수를 줄일 수 있어서 코드 가독성에 효율성(코딩 간결화를 위해서 최적화 조건임) BUT 다형성 해결 불가(결국 다형성은 생성자 주입 방식 진행)
        // @RequiredArgsConstructor : @ NonNull 어노테이션 , final 키워드가 붙어있는 대상으로 생성자 생성
        // @NonNull    // 비어진 값이 없으면 x ! (final으로 대체 가능)
        // private final MemberService memberService;

    // 생성자가 한개밖에 없는 경우 @Autowired 생략 가능
    private final MemberService memberService;
    private final MemberMemoryRepository memberMemoryRepository;

    @Autowired
    public MemberController(MemberService memberService, MemberMemoryRepository memberMemoryRepository) {
        this.memberService = memberService;
        this.memberMemoryRepository = memberMemoryRepository;
    }

    @GetMapping("/")
    public String home() {
        return "member/home";
    }
    //회원 목록 조회
    @GetMapping("/member/list")
    public String memberList(MemberResDto resDto, Model model) {
        List<MemberResDto> memberList = memberService.memberList();
        model.addAttribute("memberList", memberList);
        return "member/memberList";
    }
    // 회원 상세 조회
    // url : member/1, member/2
    @GetMapping("/member/{id}")
    // int 또는 long 받을 경우 spring에서 형변환 적용(string -> long, int)
    public String memberDetail(@PathVariable Long id, Model model) {
        MemberDetailResDto memberDetailResDto = memberService.memberDetail(id);
        model.addAttribute("member", memberDetailResDto);
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

    // http://localhost:8080/member/create
    @PostMapping("/member/create")
    //@ResponseBody
    public String memberCreatePost(@ModelAttribute MemberReqDto dto, Model model) {
        try {
            memberService.memberCreate(dto);
            return "redirect:/member/list"; // 화면 리턴이 아닌 url 재호출
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberError";
        }
    }





}
