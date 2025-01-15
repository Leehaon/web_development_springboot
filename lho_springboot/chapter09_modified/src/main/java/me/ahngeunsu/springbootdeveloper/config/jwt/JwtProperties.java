package me.ahngeunsu.springbootdeveloper.config.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties("jwt")     // 자바 클래스에서 프로파티값을 가져와 사용하는 에너테이션
public class JwtProperties {
    private String issuer;
    private String secretKey;

    /*
        이렇게 하면 issuer 필드에서 application.yml에서 설정한 jwt.issuer값이,
        secretKey에는 jwt.secret_key값이 매핑됩니다.

        다 하신 분은 동일한 패키지 내에 TokenProvider.java를 생성
     */
}
