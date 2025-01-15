package me.ahngeunsu.springbootdeveloper.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddUserRequest {
    private String email;
    private String password;
}
/*
    AddUserRequest 객체를 argument로 받는 회원 정보 추가 메서드를 작성
 */
