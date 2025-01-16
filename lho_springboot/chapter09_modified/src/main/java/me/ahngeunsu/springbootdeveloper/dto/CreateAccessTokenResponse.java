package me.ahngeunsu.springbootdeveloper.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateAccessTokenResponse {
    private String accessToken;
}
/*
    entity -> repository -> service -> dto -> controller

    이제 요청을 받고 처리할 컨트롤러 생성
    /api/token POST 요청이 오면 토큰 서비스에서 리프레시 토큰을
    기반으로 새로운 액세스 토큰을 만들어주면 됩니다.

    TokenApiController.java
 */