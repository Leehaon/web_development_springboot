package me.ahngeunsu.springbootdeveloper.config;

import lombok.RequiredArgsConstructor;
import me.ahngeunsu.springbootdeveloper.service.UserDetailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final UserDetailService userService;

    // 1. 스프링 시큐리티 기능 비활성화
    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring()
                .requestMatchers(toH2Console()) //import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;
                .requestMatchers(new AntPathRequestMatcher("/static/**"));
    }

    // 2. 특정 HTTP 요청에 대한 웹 기반 보안 구성
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeRequests(auth -> auth             // 3. 인증 / 인가 설정
                        .requestMatchers(
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/signup"),
                                new AntPathRequestMatcher("/user")
                        ).permitAll()
                        .anyRequest().authenticated())
                .formLogin(formLogin -> formLogin   // 4. 폼 기반 로그인 설정
                        .loginPage("/login")
                        .defaultSuccessUrl("/articles")
                )
                .logout(logout -> logout              // 5. 로그아웃 설정
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                )
                .csrf(AbstractHttpConfigurer::disable)                          // 6.
                .build();
    }

    // 7. 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http,
                                                       BCryptPasswordEncoder bCryptPasswordEncoder,
                                                       UserDetailService userDetailService) throws Exception {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService);      // 8. 사용자 정보 서비스 설정
        authProvider.setPasswordEncoder(bCryptPasswordEncoder);
        return new ProviderManager(authProvider);
    }

    // 9. 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
/*
     1. 스프링 시큐리티의 모든 기능을 사용하지 않게 설정하는 코드
        즉, 인증 인가 서비스를 모든 곳에 적용하지는 않음.
        일반적으로 정적 리소스(이미지, html 파일)에 설정.
        정적 리소스만 스프링 시큐리티 사용을 비활성화 하는데, static 하위 경로에 있는 리소스와
        h2 데이터를 확인하는 데 사용하는 h2-console 하위 url을 대상으로 .ignoring() 메서드

    2. 특정 HTTP 요청에 대한 웹 기반 보안을 구성. 이 메서드에서 인증 / 인가 및 로그인, 로그아웃 관련 설정

    3. 특정 경로에 대한 액세스 설정
        requestMatcher() : 특정 요청과 일치하는 url에 대한 액세스를 설정
        permitAll() : 누구나 접근 가능하게 설정. 즉, "/login", "/signup", "/user"로 요청이 오면
            인증 / 인가 없이도 접근 가능
        anyRequest() : 위에서 설정한 url 이외의 요청에 대해서 설정
        authenticated() : 별도의 인가는 필요하지 않지만 인증이 성공된 상태여야 접근 가능

    4. 폼 기반 로그인을 설정
        loginPage() : 로그인 페이지 경로를 설정
        defaultSuccessUrl() : 로그인이 완료 되었을 때 이동할 경로를 설정 -> /articles

    5. 로그아웃 설정.
        logoutSuccessUrl() : 로그아웃이 완료되었을 때 이동할 경로를 설정
        invalidHttpSesstion() : 로그아웃 이후에 세션을 전체 삭제할지 여부를 설정 -> 저희는 true설정

    6. csrf 설정을 비활성화 - CSRF 공격을 방지하기 위해서 사용하는건데, 실습 좀 편하게 하려고 비활성화 해놨습니다.
        (정보처리기사필기에서 본적있음)

    7. 인증 관리자 관련 설정. 사용자 정보를 가져올 서비스를 재정의하거나, 인증 방법, 예를 들어
        LADP, JDBD 기반 인증 등을 설정할 때 사용

    8. 사용자 서비를 설정
        userDetailService : 사용자 정보를 가져올 서비스를 설정. 이 때 설정하는 서비스 클래스는 반드시
            UserDetailsService를 상속받은 클래스여야 함.
        passwordEncoder : 비밀 번호 인코더를 설정

    9. 패스워드 인코더를 빈으로 등록


    회원 가입 구현
        서비스 메서드 작성할겁니다 -> 사용자 정보를 담고 있는 객체를 만들 수 있도록 해야합니다.
            -> dto 패키지에 AddUserRequest.java 파일을 생성

 */
