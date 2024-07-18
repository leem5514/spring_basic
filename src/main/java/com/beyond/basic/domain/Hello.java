package com.beyond.basic.domain;

import lombok.*;

//@Getter
//@Setter
@NoArgsConstructor // 기본생성자 어노테이션으로 생성
// builder 패턴을 쓸 떄 allArgisConstructor 사용 필 - 모든 변수를 사용하여야 하기 때문에
@Builder
@AllArgsConstructor // 모든 매개변수를 사용한 생성자를 만드는 어노테이션(@NoArgsConstructor 와 같이 써야함)
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

    // Builder패턴 직접 구현
    // 빌더 직접 적용
    public Hello(HelloBuilder helloBuilder) {
        this.name = helloBuilder.name;
        this.email = helloBuilder.email;
        this.password = helloBuilder.password;
    }
    public static HelloBuilder builder() {
        return new HelloBuilder();
    }

    public static class HelloBuilder {
        private String name;
        private String email;
        private String password;

        public HelloBuilder name(String name) {
            this.name = name;
            return this;
        }
        public HelloBuilder email(String email) {
            this.email = email;
            return this;
        }
        public HelloBuilder password(String password) {
            this.password = password;
            return this;
        }

        public Hello build() {
            return new Hello(this);
        }
    }
}
