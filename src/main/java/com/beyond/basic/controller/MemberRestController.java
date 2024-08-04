package com.beyond.basic.controller;

import com.beyond.basic.domain.*;
//import com.beyond.basic.repository.MemberMemoryRepository;
import com.beyond.basic.repository.MemberRepository;
import com.beyond.basic.repository.MyMemberRepository;
import com.beyond.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.OneToMany;
import java.util.List;

@RestController
// restController 의 경우 모든 메서드 상단에 @ResponseBody가 붙는 효과 발생
@RequestMapping("/rest")
@Api(tags = "회원관리서비스")
public class MemberRestController {

    private final MemberService memberService;
    private final MyMemberRepository memberRepository;
    @Autowired
    public MemberRestController(MemberService memberService, MyMemberRepository memberRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
    }

    @GetMapping("/member/list")
    public ResponseEntity<Object> memberList() {
        List<MemberResDto> memberList = memberService.memberList();
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", memberList);

        return new ResponseEntity<>(commonResDto, HttpStatus.OK);
    }
    @GetMapping("/member/detail/{id}")
    public ResponseEntity<?> memberDetail(@PathVariable Long id) {

        MemberDetailResDto memberDto = memberService.memberDetail(id);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK, "no error", memberDto);
        return new ResponseEntity<>(commonResDto, HttpStatus.OK);

    }
    @PostMapping("/member/create")
    public ResponseEntity<?>  memberCreatePost(@RequestBody MemberReqDto dto) {
//        try {

            memberService.memberCreate(dto);
            CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED, "success created", null);

            return new ResponseEntity<>(commonResDto, HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//            CommonErrorDto commonErrorDto = new CommonErrorDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//            return new ResponseEntity<>(commonErrorDto, HttpStatus.CREATED);
//        }
    }
    // 수정은 2가지 요청방식 : PUT, PATCH 요청
    // PATCH 요청은 부분수정, PUT 요청은 덮어쓰기

    @PatchMapping("/member/pw/update")
    public String pwUpdate(@RequestBody MemberUpdateDto dto ) {
        memberService.pwUpdate(dto);
        return "ok";
    }


    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable Long id) {
        memberService.delete(id);
        return "ok";
    }

    // 화면 Return -> MVC 패턴 (JSP 또는 타임리프) -> 데이터를 return 해서 : rest -api 방식
    @GetMapping("/member/text")
    public String memberText() {
        return "ok";
    }

    // LAZY(지연로딩), EAGER(즉시로딩) 테스트
    @GetMapping("member/post/all")
    public void memberPostAll() {
        System.out.println("memberRepository: " + memberRepository.findAll());

        List<Member> memberList = memberRepository.findAll();
        for (Member member: memberList) {
            System.out.println("member size: " + member.getPosts().size());
        }
    }
    // LAZY 가 N+1에 관한 완전한 이슈를 해결해 주지 않음 하지만 참조 하지 않은 경우에 대한 해결은 가능
    // 참조영역에 대한 N+1 이슈는 FETCH JOIN 을 통해서 해결 가능
}
