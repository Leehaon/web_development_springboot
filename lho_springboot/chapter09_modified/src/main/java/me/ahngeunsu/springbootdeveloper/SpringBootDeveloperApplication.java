package me.ahngeunsu.springbootdeveloper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}
/*
    1. 사전 지식 : 토큰 기반 인증

    사용자가 서버에 접근할 때 이 사용자가 인증된 사용자인지 확인하는 방법 중 하나로,
        대표적으로 서버 기반 인증과 토큰 기반 인증이 있음.

        토큰 기반 인증 :
            *토큰 : 서버에서 클라이언트를 구분하기 위한 유일한 값

            서버가 토큰을 생성해서 클라이언트에게 제공하면, 클라이언트는 이 토큰을 가지고 있다가
            여러 요청을 해당 토큰과 함께 신청함. 이후 서버는 토큰을 확인하여 유효한 사용자인지를
            검증하게 됨.

            chapter08에서는 스프링 시큐리티를 이용하여 세션 기반 인증을 사용해 사용자마다
            사용자의 정보를 담은 세션을 생성하고 저장해서 인증했음 -> 세션 기반 인증

            토큰을 전달하고 인증 받는 과정
                토큰은 요청과 응답에 함께 보내는데,

                1) 클라이언트가 아이디와 비밀번호를 서버에게 전달하면서 인증 요청 ->
                2) 서버는 아이디와 비밀번호를 확인, 유효한 사용자라면 토큰을 생성하면서 응답 ->
                3) 클라이언트는 서버에서 준 토큰을 저장 ->
                4) 이후 인증이 필요한 API를 사용할 때 토큰을 함께 보냄 ->
                5) 서버는 토큰이 유효한지 검증
                6) 토큰이 유효하다면 클라이언트의 요청한 내용을 처리

            토큰 기반 인증의 특징
                1) 무상태성
                    사용자의 인증 정보가 담겨 있는 토큰이 서버가 아닌 클라이언트에 있으므로
                        서버에 저장할 필요 x
                    서버가 데이터를 유지하고 있으려면 그만큼 자원을 소비해야하는데,
                    토큰 기반 인증에서는 클라이언트에서 인증 정보가 담긴 토큰을 생성하고 인증하여
                    서버 입장에서는 클라이언트의 인증 정보를 저장하거나 유지할 필요가 x
                    그래서 완전한 무상태(stateless)로 효율적인 검증 가능

                2) 확장성
                    무상태성과 관련하여, 서버를 확장할 때 상태 관리를 신경쓸 필요가 x
                    세션 기반 인증은 각각 API에서 인증을 해야하는 것과 달리 토큰 기반 인증은
                    토큰을 가지는 주체가 서버가 아닌 클라이언트이기 때문에 가지고 있는 하나의
                    토큰으로 결제 서버와 주문 서버에게 요청을 보내는 것이 가능.
                    추가로 페이스북 로그인, 구글 로그인과 같이 토큰 기반 인증을 사용하는
                    다른 시스템에 접근해 로그인 방식을 확장할 수도 있고,
                    이를 활용해 다른 서비스에 권한을 공유할 수도 있음.

                3) 무결성
                    토큰 기반 인증 방식은 HMAC(Hash-based Message Authentication) 기법이라고도 하는데,
                    토큰을 발급한 이후에는 토큰 정보를 변경하는 행위를 할 수 없음.
                    만약 토큰을 한 글자라도 변경하면 서버에서는 유효하지 않은 토큰이라고 판단합니다.

            JWT(JSON Web Token) -> json형태로 주고받는거죠
                발급받은 JWT를 이용해 인증을 하려면 HTTP 요청 헤더 중에 Authorization 키 값에
                Bearer + JWT 토큰 값을 넣어 보내야 합니다.

                JWT 구조 : 온점(.)을 기준으로 헤더(header), 내용(payload), 서명(signature)로 이루어짐.
                    aaaa . bbbb . cccc
                    헤더    내용    서명

                    1) 헤더에는 토큰의 타입과 해싱 알고리즘을 지정하는 정보를 포함함.
                    🎈 토큰 타입과 해싱 알고리즘 지정 예시

                    {
                        "typ": "JWT",
                        "alg": "HS256"
                    }
                        구성 :
                            typ : 토큰의 타입 지정 -> JWT "문자열"
                            alg : 해싱 알고리즘 지정

                    2) 내용에는 토큰과 관련된 정보를 담습니다. 내용의 한 덩어리를 클레임(claim)
                        이라고 하며, 키-값의 한쌍으로 이루어짐.
                        등록된 클레임 / 공개 클레임 / 비공개 클레임으로 나뉨.

                        구성 :
                            iss : 토큰 발급자(issuer)
                            sub : 토큰 제목(subject)
                            aud : 토큰 대상자(audience)
                            exp : 토큰의 만료 시간(expiration).
                                시간은 NumericDate 형식 (20250115),
                                항상 현재 시간 이후로 설정해야 합니다.
                            nbf : 토큰의 활성 날짜와 비슷한 개념. Not Before를 의미함.
                                이 날짜가 지나기 전까지는 토큰이 처리되지 않음.
                            iat : 토큰이 발급된 시간. issued at을 의미
                            jti : 토큰의 고유 식별자로서 주로 일회용 토큰에 사용

                        공개 클레임(public claim) : 공개되어도 상관없는 클레임.
                            충돌을 방지할 수 있는 이름을 가져야 하며 보통 URI
                        비공개 클레임(private claim) : 공개되면 안되는 클레임.
                            클라이언트와 서버간의 통신에 사용

                        🎈 JWT 예시
                        {
                            "iss": "maybeags@gmail.com" // 등록된 클레임
                            "iat": 1622370678,
                            "exp": 1622372678,
                            "https://ahngeunsu.com/jwt_claims/is_admin": true,  // 공개 클레임
                            "email": "maybeags@gmail.com",          // 비공개 클레임
                            "hello": "안녕하세요😀"
                        }

                    iss, iat, exp는 JWT 자체에서 등록된 클레임
                    URI로 네이밍된 https://ahngeunsu.com/jwt_claims/is_admin : 공개클레임
                    등록/공개 클레임 제외 나머지는 전부 비공개 클레임에 해당

                    3) 서명 : 해당 토큰이 조작되었거나 변경되지 않았음을 확인하는 용도로 사용.
                        헤더의 인코딩 값과 내용의 인코딩 값을 합친 후에 비밀키를 사용해 해시값을 생성

                        토큰 유효 기간
                            토큰 유효 기간이 하루라면 하루동안 모든 인증을 할 수 있고,
                            토큰 유효 기간이 너무 짧다면 사용자가 불편함.
                                -> 리프레시 토큰의 개념이 발생
                                * 리프레시 토큰 : 액세스 토큰과 별개로 사용자를 인증하기 위한 용도가 아니라
                                    액세스 토큰이 만료되었을 때 액세스 토큰을 발급하기 위해 사용.

        2. JWT 서비스 구현
            의존성과 토큰 제공자를 추가하고 리프레시 토큰 도메인 토큰 필터를 구현하면
            -> JWT 서비스를 사용할 준비가 됐다고 볼 수 있습니다.


            build.gradle로 이동해서 의존성 추가할겁니다


            토큰 제공자를 추가할겁니다.
                jwt를 사용해서 JWT를 생성하고 유효한 토큰인지 검증하는 클래스 추가할 예정
                    JWT 토큰을 만들려면 이슈 발급자(issuer), 비밀키(secret_key)를 필수로 설정
                    application.yml로 이동합니다.




*/