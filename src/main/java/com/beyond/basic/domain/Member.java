package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
// entity 어노테이션을 통해서 엔티티 메니져에게 객체 관리를 위임
// 래닫 클래스명으로 테이블 및 컬럼을 자동생성하고 각종 설정정보 위임;
@Entity
public class Member {
    @Id // pk 설정(必)
    // IDENTITY : AUTO_INCREMENT
    // AUTO : JPA 에게 적절한 전략을 선택하도록 맡기는 것.
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // BigINT 변환
    // String은 Varchar(255)으로 기본 변환 . name변수명이 name 컬럼명으로 변환
    private String name;
    @Column(nullable = false, length = 50, unique = true) // nullable = false -> notnull제약조건
    private String email;
    // @Column(name = "pk") 이렇게 할 수 있으나, 컬럼명과 변수명은 일치시키는 것이 혼선을 생길 수 있기 떄문에 비 권장
    private String password;

}
