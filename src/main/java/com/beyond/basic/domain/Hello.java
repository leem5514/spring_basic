package com.beyond.basic.domain;

import lombok.*;

//@Getter
//@Setter
@NoArgsConstructor // 기본생성자 어노테이션으로 생성
// @AllArgsConstructor // 모든 매개변수를 사용한 생성자를 만드는 어노테이션(@NoArgsConstructor 와 같이 써야함)
@Data // getter, setter, toString 합친 기능
public class Hello {

    private String name;
    private String email;
    private String password;


    // but 이 코드 너무 번거롭고 거슬려! = 어노테이션으로 해결가능 ദ്ദി ( ᵔ ᗜ ᵔ )
//    @Override
//    public String toString() {
//        return "이름 :" + name + ", 이메일 : " + email + ", 비밀번호 : " + password + "]";
//    }
}
