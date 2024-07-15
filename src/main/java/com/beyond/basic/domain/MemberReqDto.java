package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MemberReqDto {
    private String name;
    private String email;
    private String password;


    // DTO에서 entity으로 변환
    // 추 후는 빌더 패턴으로 변환

    public Member toEntity() {
//        Member member =  new Member();
//        member.setName(this.name);
//        member.setEmail(this.email);
//        member.setPassword(this.password);

    Member member = new Member(this.name, this.email, this.password); // Member 객체에서 constructor 생성
        return member;
    }

}
