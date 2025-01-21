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
    //https://developers.google.com/identity/branding-guidelines 여기서 다운받을 수 있음.

    @GetMapping("/signup")
    public String signup() {
        return "signup";
    }
}
