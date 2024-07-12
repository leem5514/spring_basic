package com.beyond.basic.service;

import com.beyond.basic.domain.Member;
import com.beyond.basic.domain.MemberDetailResDto;
import com.beyond.basic.domain.MemberReqDto;
import com.beyond.basic.domain.MemberResDto;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//input 값의 검증 및 실질적 비즈니스 로직은 서비스 계층에서 실행
@Service // 서비스 계층임을 표현함과 동시 싱글톤객체로 생성
// Transactional 어노테이션을 통해서 모든 메서드에 트랜잭션 적용, 만약 예외가 발생 시 롤백처리 자동화
@Transactional
public class MemberService {
    // springdataJpaRepository 홀로만 구현할 경우!
    //private final MyMemberRepository memberRepository;
//    public MemberService(MyMemberRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }

    // 다형성 구현
    private final MemberRepository memberRepository;

    @Autowired // 싱글톤 객체를 주입(DI) 받는다라는 것을 의미
//    public MemberService(MemberMemoryRepository memberMemoryRepository) {
//        this.memberRepository = memberMemoryRepository;
//    }
    public MemberService(MemberSpringDataJpaRepository memberMemoryRepository) {
        this.memberRepository = memberMemoryRepository;
    }

    public void memberCreate(MemberReqDto dto) {
        if(dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
        Member member = new Member();
        member.setEmail(dto.getEmail());
        member.setPassword(dto.getPassword());
        member.setName(dto.getName());

        memberRepository.save(member);
    }

   public MemberDetailResDto memberDetail(Long id) {
        Optional<Member> optMember = memberRepository.findById(id);
        MemberDetailResDto dto = new MemberDetailResDto();
        // client에게 적절한 예외메시지와 상태코드 주는것이 주 목적
        // 또한 예외를 강제 발생시킴으로서 적절한 롤백처리 하는것도 주요목적
        Member member = optMember.orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));

        dto.setId(member.getId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPassword(member.getPassword());
        return dto;
   }

    public List<MemberResDto> memberList() {
        List<MemberResDto> memberResDto = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for(Member member : memberList) {
            MemberResDto resDto = new MemberResDto();
            resDto.setId(member.getId());
            resDto.setName(member.getName());
            resDto.setEmail(member.getEmail());
            memberResDto.add(resDto);
        }
        return memberResDto;
    }
}
