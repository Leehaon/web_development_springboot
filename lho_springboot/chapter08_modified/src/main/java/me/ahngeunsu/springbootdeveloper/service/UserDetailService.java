package me.ahngeunsu.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.ahngeunsu.springbootdeveloper.domain.User;
import me.ahngeunsu.springbootdeveloper.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    // 사용자 이름(email)으로 사용자 정보를 가져오는 메서드
    @Override
    public User loadUserByUsername(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException((email)));
    }
}
/*
    스프링 시큐리티에서 사용자 정보를 가져오는 UserDetailsService 인터페이스를 구현합니다
    (우리가 작성한 class명 : UserDetailService.java)
    필수로 구현해야하는 loadUserByUsername() 메서드를 오버라이딩해서 사용자 정보를 가져오는
    로직 작성했습니다.

    시큐리티 설정 파일을 만들 예정
         springbootdeveloper 패키지 우클릭 -> new package -> config
         WebSecurityConfig.java 클래스 생성
 */
