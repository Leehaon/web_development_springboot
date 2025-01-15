package me.ahngeunsu.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
    /*
        GET 요청으로 /login 경로로 들어오면 login() 메서드가 login.html을,
        마찬가지 요청으로 /signup 경로로 들어오면 signup() 메서드가 signup.html을 반환

        resources/templates에 login.html 생성
     */

    // 로그아웃 관련
}
