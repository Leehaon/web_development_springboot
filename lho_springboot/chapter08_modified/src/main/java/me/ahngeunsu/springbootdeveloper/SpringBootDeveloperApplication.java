package me.ahngeunsu.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing      // created_at, updated_at을 자동 업데이트해주는 애너테이션
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
/*
    학습 목표 : 로그인 / 회원 가입 기능을 구현
        회원 가입 구조는 로그인 구조와 유사합니다.

        /login 요청이 들어올 때,
        -> UserViewController가 해당 요청에 대한 분기 처리를 하고 WebSecurityConfig에 설정한 보안 관련
            내용들을 실행
        -> UserDetailsService를 실행하면 요청을 성공했을 때
        -> defaultSuccessUrl로 설정한 /articles로 리다이렉트 하거나 csrf를 disable하거나 하는 등의 작업

        UserDetailsService에서는 loadUserByUsername() 메서드를 실행하여 이메일로 유저를 찾고 반환.

        여기서 유저는 User 클래스의 객체이고 UserRepository에서 실제 데이터를 가져 올겁니다.

        회원 가입 역시 유사하게 구성할겁니다.

        로그아웃
        /logout 요청이 들어오면
        -> UserApiController클래스에서 로그아웃 로직을 실행할 예정.
        그 로그아웃 로직 : SecurityContextLogoutHander에서 제공하는 logout() 메서드를 실행할 예정.

        1. 스프링 시큐리티(Spring Security)
            1) 인증 vs 인가
                (1) 인증(Authentication) : 사용자의 신원을 입증하는 과정
                    예를 들어 사용자가 사이트에 로그인할 때 누구인지 확인하는 과정을 인증이라고 함.

                (2) 인가(Authorization) : 사이트의 특정 부분에 접근할 수 있는지 권한을 확인하는 작업
                    예를 들어 관리자는 관리자 페이지에 들어갈 수 있지만 일반 사용자는 관리자 페이지에
                    들어갈 수 없음.

                인증과 인가 관련 코드를 아무런 도구 없이 작성하려면 복잡하기 때문에 스프링 시큐리티를 사용함.

            2) 스프링 시큐리티
                : 스프링 기반 애플리케이션의 보안을 담당하는 스프링 하위 프레임워크 보안 관련 옵션을 다수
                    제공해줌.
                    CSRF, 세션 공격을 방어해주고(-> 정처기 등에 자주 나옴), 요청 헤더도 보안 처리를 해주므로
                    보안 관련 개발을 하는데 부담을 덜어주는 편.

            3) 필터 기반으로 동작하는 스프링 시큐리티
                스프링 시큐리티의 풀 로그인 과정을 설정하는 건(코드로 작성하는 건) 그렇게 어렵지 않을건데,
                내부적으로 아까 보여드린 그림처럼 꽤 복잡한 과정을 거치게 될겁니다.

                모두 외워야 하는 거 x, 다만 로그인 과정이 어떤 흐름으로 이어지는 지를 이해하면 더 잘 활용 가능
                일단은 회원 가입 / 로그인 관련 부분을 코딩으로 풀어나갈 예정


        2. 회원 도메인 만들기
            스프링 시큐리티를 활용한 인증, 인가 기능 구현 예정.
            회원 정보를 저장할 테이블을 만들고,
            테이블과 연결할 도메인을 만들고,
            이 테이블과 연결할 회원 엔티티를 만들 겁니다.
            회원 엔티티와 연결되어 데이터를 조회하게 해줄 리포지토리가 추가되구요, 마지막으로
            스프링 시큐리티에서 사용자 정보를 가져오는 서비스를 구현할 예정

            -> build.gradle에서 의존성 추가할게요.












 */