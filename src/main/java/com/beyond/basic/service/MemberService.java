package com.beyond.basic.service;

import com.beyond.basic.domain.*;
import com.beyond.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
    public MemberService(MemberJpaRepository memberMemoryRepository) {
        this.memberRepository = memberMemoryRepository;
    }

    public void memberCreate(MemberReqDto dto) {
        if(dto.getPassword().length() < 8) {
            throw new IllegalArgumentException("비밀번호가 너무 짧습니다.");
        }
//        Member member = new Member();
//        member.setEmail(dto.getEmail());
//        member.setPassword(dto.getPassword());
//        member.setName(dto.getName());
        // DTO에서 코드의 중복성을 막기위헤서 객체적 언어로 변경
        Member member = dto.toEntity();

        memberRepository.save(member);
    }

   public MemberDetailResDto memberDetail(Long id) {
        Optional<Member> optMember = memberRepository.findById(id);
//        MemberDetailResDto dto = new MemberDetailResDto();
        // client에게 적절한 예외메시지와 상태코드 주는것이 주 목적
        // 또한 예외를 강제 발생시킴으로서 적절한 롤백처리 하는것도 주요목적
        Member member = optMember.orElseThrow(() -> new EntityNotFoundException("없는 회원입니다."));

//        dto.setId(member.getId());
//        dto.setName(member.getName());
//        dto.setEmail(member.getEmail());
//        dto.setPassword(member.getPassword());
//        LocalDateTime createdTime = member.getCreatedTime();
//        String value = createdTime.getYear()+"년" + createdTime.getMonthValue()+"월"+createdTime.getDayOfMonth()+"일";
//        dto.setCreatedTime(value);
        System.out.println("글쓴이의 쓴 글의 개수 " + member.getPosts().size());
        for(Post p : member.getPosts()){
            System.out.println("글의제목" + p.getTitle());
        }
        return member.detFromEntity();
   }

    public List<MemberResDto> memberList() {
        List<MemberResDto> memberResDtos = new ArrayList<>();
        List<Member> memberList = memberRepository.findAll();
        for(Member member : memberList) {
            memberResDtos.add(member.listFromEntity());
//            MemberResDto resDto = new MemberResDto();
//            resDto.setId(member.getId());
//            resDto.setName(member.getName());
//            resDto.setEmail(member.getEmail());

        }
        //return memberResDto;
        return memberResDtos;
    }

    public void pwUpdate(MemberUpdateDto dto ) {
        Member member = memberRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("member is not found"));
        member.updatePw(dto.getPassword());

        // 기존 객체를 조회 후 수정한 다음 save 시에는 jpa update
        memberRepository.save(member);

    }
    public void idDelete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("member is not found"));
        memberRepository.delete(member);
        // member.updateDelYn("Y");
        /// memberRepository.save(member);
    }
}
