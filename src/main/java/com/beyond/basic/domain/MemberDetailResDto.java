package com.beyond.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDetailResDto {
    private long id;
    private String name;
    private String email;
    private String password;
    private String createdTime;
}
