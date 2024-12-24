package me.LeeHaOn.springbootdeveloper;
/*
    모든 프로젝트에는 main에 해당하는 클래스가 존재합니다 -> 실행용 클래스
    이제 이 class를 main 클래스로 사용할 예정입니다.
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}