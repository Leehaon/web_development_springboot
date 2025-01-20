package me.ahngeunsu.springbootdeveloper.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    // 기존 로그인 페이지
//    @GetMapping("/login")
//    public String login() {
//        return "login";
//    }

    // oauth 관련 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "oauthLogin";
    }

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}