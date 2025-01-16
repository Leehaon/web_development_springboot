package me.ahngeunsu.springbootdeveloper.config.jwt;

import io.jsonwebtoken.Jwts;
import me.ahngeunsu.springbootdeveloper.domain.User;
import me.ahngeunsu.springbootdeveloper.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Duration;
import java.util.Date;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenProviderTest {
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtProperties jwtProperties;

    // 1. generateToken() 검증 테스트
    @DisplayName("generateToken() : 유저 정보와 만료 기간을 전달해 토큰 생성 가능")
    @Test
    void generateToken() {
        // given
        User testUser = userRepository.save(User.builder()
                .email("user@gmail.com")
                .password("test")
                .build());

        // when
        String token = tokenProvider.generateToken(testUser, Duration.ofDays(14));

        // then
        Long userId = Jwts.parser()
                .setSigningKey(jwtProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .get("id", Long.class);

        assertThat(userId).isEqualTo(testUser.getId());
        //import static org.assertj.core.api.Assertions.assertThat;
    }

    @DisplayName("validToken() : 유효한 토큰인 때에 유효성 검증에 성공")
    @Test
    void validToken_validToken() {
        // given
        String token = JwtFactory.withDefaultValues().createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isTrue();
    }

    @DisplayName("validToken() : 만료된 토큰일 때에 유효성 검증에 실패")
    @Test
    void validToken_invalidToken() {
        // given -> 일부러 default가 아니라 실패하는 token을 생성하기 때문에 위의 메서드와 given이 다릅니다.
        String token = JwtFactory.builder()
                .expiration(new Date(new Date().getTime()
                        - Duration.ofDays(7).toMillis()))
                .build()
                .createToken(jwtProperties);

        // when
        boolean result = tokenProvider.validToken(token);

        // then
        assertThat(result).isFalse();
    }

    // 3. getAuthentication() 검증 테스트
    @DisplayName("getAuthentication() : 토큰 기반으로 인증 정보를 가져온다.")
    @Test
    void getAuthentication() {
        // given
        String userEmail = "user@gmail.com";
        String token = JwtFactory.builder()
                .subject(userEmail)
                .build()
                .createToken(jwtProperties);

        // when
        //import org.springframework.security.core.Authentication;
        Authentication authentication = tokenProvider.getAuthentication(token);

        // then -> 참조자료형캐스팅 응용
        assertThat(((UserDetails) authentication.getPrincipal()).getUsername())
                .isEqualTo(userEmail);
    }

    // 4. getUserId()
    @DisplayName("getUserId() : 토큰으로 유저ID를 가져올 수 있다.")
    @Test
    void getUserId() {
        // given
        Long userId = 1L;
        String token = JwtFactory.builder()
                .claims(Map.of("id", userId))
                .build()
                .createToken(jwtProperties);

        // when
        Long userIdByToken = tokenProvider.getUserID(token);

        // then
        assertThat(userIdByToken).isEqualTo(userId);
    }
}
/*
    1. generateToken()
        given : 토큰에 유저 정보를 추가하기 위한 테스트 유저 생성
        when : 토큰 제공자의 generateToken() 메서드를 호출 -> 토큰 생성
        then : jjwt 라이브러리를 사용하여 토큰을 복호화
            -> 토큰을 만들 때 클레임으로 넣어둔 id 값이 given절에서 만든
            유저 ID와 동일한지 확인

    2.
        1)  validToken_validToken()
        given : jjwt 라이브러리를 사용, 토큰 생성. -> 만료 시간은 현재 시간으로부터
            14일 뒤로 만료되지 않은 토큰으로 생성 -> Default로 만들었습니다 -> JwtFactory.java

        when : 토큰 제공자의(tokenProvider) validToken() 메서드를 호출해 유효한 토큰인지 검증한 뒤
            결과값을 boolean값으로 반환
        then : 반환값이 true인지 확인

        2)  validToken_invalidToken()
        given : jjwt 라이브러리를 사용, 토큰 생성. -> 만료 시간은 1970년 1월 1일부터
            현재 시간을 밀리초 단위로 치환한 값(new Date().getTime())에 1000을 빼서
            이미 만료된 토큰으로 설정.
        when : 토큰 제공자의(tokenProvider) validToken() 메서드를 호출해 유효한 토큰인지 검증한 뒤
            결과값을 boolean값으로 반환
        then : 반환값이 false인지 확인

    3. getAuthentication()
        given : jjwt 라이브러리를 사용해 토큰 생성, 이때 subject는 "user@gmail.com"
        when : getAuthentication() 메서를 호출, 인증 객체를 반환
        then : 반환 받은 인증 객체의 유저이름(username)을 가져와서 given절에서 초기화한
            subject값인 user@gmail과 같은 값인지 확인

    4. getUserId()
        given : jjwt 라이브러리를 사용해 토큰 생성. 이때 클레임을 추가하여 키는 "id" 값은 1인
            유저 ID를 생성
        when : getUserId()를 호출하여 유저 ID를 반환
        then : 반환받은 유저 ID가 given 절에서 초기화한 1과 같은지를 확인.

        리프레시 토큰 구현하기
            리프레시 토큰은 데이터베이스에 저장하는 정보이므로 엔티티와 리포터지토리를 추가해야 함.
            만들 엔티티와 매핑되는 테이블 구조는

           +--------------------------------------------------------------------------------+
           |    컬럼명         |   자료형          |    null 허용    |    키     |   설명
           ---------------------------------------------------------------------------------+
           | id              |    BIGINT        |       N       |   기본키    | 일련번호, 기본키
           | user_id          |   BIGINT        |       N       |           | 유지 ID
           | refresh_token    |   VARCHAR(255)  |       N       |           | 토큰값
           +--------------------------------------------------------------------------------+
 */