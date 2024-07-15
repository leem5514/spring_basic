package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class MemberResDto {
    private Long id;
    private String name;
    private String email;

//    public MemberResDto(String name, String email) {
//        this.name = name;
//        this.email = email;
//    }
}
