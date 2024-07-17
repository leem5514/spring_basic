package com.beyond.basic.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor // 기본생성자는 JPA 에서 必 이다
//@AllArgsConstructor
// entity 어노테이션을 통해서 엔티티 메니져에게 객체 관리를 위임
// 해당 클래스명으로 테이블 및 컬럼을 자동생성하고 각종 설정정보 위임 (JPA으로 진행할 때 바로 붙이기!!) / JPA : 객체중심사상
// 싱글톤 객체 = 스프링 빈(bean) - > spring 에서 관리 / @Component : 클래스 단윈로 지정 ,@Bean(외부 라이브러리) : 메서드 단위로 지정, return 객체 + configuration
//

@Entity
public class Member extends BaseEntity{
    @Id // pk 설정(必) - 없는 경우 에러 발생
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

    @OneToMany(mappedBy = "member")
    private List<Post> posts;

    // db 에서는 created_time 으로 들어감
    @CreationTimestamp // DB에선는 current_timeStamp 생성x
    private LocalDateTime createdTime;

    @UpdateTimestamp
    private LocalDateTime updatedTime;


    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // password 상단에 @Setter 을 통해 특정 변수만 setter 사용이 가능하나 , 일반적으로 의도를 명확하게 한 메서드를 별도로 만들어 사용하는 것을 권장
    public void updatePw(String password) {
        this.password = password;
    }


    public MemberDetailResDto detFromEntity() {
        LocalDateTime createdTime = this.getCreatedTime();
        String value = createdTime.getYear()+"년" + createdTime.getMonthValue()+"월"+createdTime.getDayOfMonth()+"일";
        return new MemberDetailResDto(this.id, this.name, this.email, this.password, value);
    }

    public MemberResDto listFromEntity() {
        return new MemberResDto(this.id, this.name, this.email);
    }
}
