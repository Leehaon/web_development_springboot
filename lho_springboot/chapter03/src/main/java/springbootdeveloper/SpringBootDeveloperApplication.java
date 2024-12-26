package me.LeeHaOn.springbootdeveloper;
/*
    New project 생성시 주의점 :
        1. build system -> Gradle
        2. DLS -> Groovy 설정
        3. name = ArtifactId
        4. build.gradle 설정을 복사하는데, 복사후에 -> sync를 꼭꼭 눌러주셔야 합니다.
            -> SpringBootDeveloperApplication에서 @SpringBootApplication 에너테이션에 빨간줄이 있다면
                새로고침 안했을 확률이 거의 90%
        5. resources 내에 static 내에 index.html이라고 하는데, 해당 폴더명의 경우 대부분의 개발자들이 합의한 형태
        6. 앞으로의 수업 시간에는 new project 생성시에 이렇게 자셍히 풀이할 일은 자주 있지 않을 예정이니 꼭
            익혀두시기 바랍니다. -> github 본다고 되는 부분이 아닌만큼 신경써주시면 감사하겠습니다.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/*
    1. Spring vs. SpringBoot

        Enterprise Application : 대규모의 복잡한 데이터를 관리하는 애플리케이션
            많은 사용작의 요청을 동시에 처리해야 하므로 서버 성능과 안정성, 보안 이슈 등
            모든 걸 신경스면서 사이트 기능, 즉 '비지니스 로직'을 개발하기가 매우 어려워서
            등장하게 된 것이 Spring

        SpringBoot의 등장
            스프링은 매우 많은 장접을 지니고 있지만 설정이 복잡하다는 단점이 있음.
            이런 단점을 보완하고자 등장한 것이 스프링 부트

            특징 :
                1) 톰캣, 제티, 언더토우 같은 웹 애플리케이션 서버가 내장돼있어 따로 설치하지
                    않아도 독립적으로 실행 가능
                2) 빌드 구성을 단순화하는 스프링 부트 스타터를 제공
                3) XML 설정을 하지 않고 자바 코드로 작성 가능
                4) JAR를 이용해서 자바 옵션만으로 배폭사 가능
                5) 애플리케이션 모니터링 및 관리  도구인 스프링 액츄에이터(Spring Actuator) 제공

            즉, 스프링부트라는 것은 기본적으로 스프링에 속해있는 도구

            차이점 :
                1) 구성의 차이 -> 스프링은 애플리케이션 개발에 필요한 환경을 수동으로 구성하고 정의
                    하짐만 스프링부트는 스프링 코어와 스프링 MVC의 모든 기능을 자동으로 로드
                    수동으로 개발 환경을 구성할 필요가 없음.

                2) 내장 WAS 유무 -> 스프링 에플리케이션은 일반적으로 톰캣과 같은 WAS에서 배포
                    WAS : Web Application Server의 약자
                    하지만 스프링 부트는 자체적으로 WAS를 가지고 있어 JAR 파일만 만들면 별도로
                    WAS 설정을 하지 않아도 애플리케이션을 실행할 수 있음

    2. 스프링 컨셉
        1) 제어의 역전(IoC)
            Inversion of Control : 다른 객체를 직접 생성하거나 제어하는 것이 아니라
                외부에서 관리하는 객체를 가져와 사용하는 것을 의미함.
                ex) 클래스 A에서 클래스 B의 객체를 생성한다고 가정했을 때의 자바 코드
                public class A {
                    B b = new B();
                }

                위와는 다르게 클래스 B 객체를 직접 생성하는 것이 아니라 어딘가에서 받아와 사용
                실제로 스프링은 스프링 컨테이너에서 객체를 관리, 제공하는 역할을 함.
                ex) 스프링 컨테이너가 객체를 관리하는 방식의 자바 코드
                public class A {
                    private B b;
                }

        2) 의존성 주입(DI)
            Dependency Injection : 어떤 클래스가 다른 클래스에 의존한다는 의미

            @Autowired 에터테이션이 증요합니다 -> 스프링 컨테이너에 있는 빈(Bean)을 주입하는 역할
            Bean : 스프링 컨테이너가 관리하는 객체 -> 추후 한 번 더 수업할 예정입니다.

            ex) 객체를 주입 받는 모습 예제
            public class A {
                @Autowired
                B b;
            }

            3) 빈과 스프링 컨테이너
                3)-1. 스프링 컨테이너 : 빈이 생성되고 소멸되기 까지의 생명주기를 관리함.
                    또한 @Autowired와 같은 에너테이션을 사용해 빈을 주입받을 수 있게 DI를 지원함.

                3)-2. 빈 : 스프링 컨테이너가 생성하고 관리하는 객체 -> 이상의 코드들에서 B가 빈에 해당
                    스프링은 빈을 스프링 컨테이너에 등록하기 위해 XML 파일 설정, 에너테이션 추가 동의
                    방법을 제공하는 데, 이 것이 의미하는 바는
                        1- 빈을 등록하는 방법은 여러가지이다.
                        2- 수업 중 우리가 사용하는 방식은 에너테이션이다.
                    ex) 클래스를 빈으로 등록하는 방법 예제
                    @Component
                    public class MyBean{}

                        이상과 같이 @Component라는 에터테이션을 붙이면 MyBean 클래스가 빈으로 등록됨
                        이후 스프링 컨테이너에서 이 클래스를 관라하게 되고, 빈의 이름은 첫 문자를
                        소문자로 바꾸어서 관리함. 즉 클래스 MyBean의 빈 이름은 MyBean이 됨.

                        일반적으로는 스프링이 제공해주는 객체로 받아들이시면 됩니다!!!

            4) 관점 지향 프로그래밍(AOP)
                Aspect-oriented Programming : 프로그래밍에 대한 관심을 핵식 관점 / 부가 관점
                    으로 나누어 모들화함을 의미.

                    ex) 계좌 이체, 고객 관리하는 프로그램이 있다고 가정할 때, 각 프로그램에는
                        로깅 조직, 즉 지금까지 일어난 일을 기록하기 위한 로직과 여러 데이터를 관리
                        하기 위한 데이터 베이스 연결 로직이 포함됨. 이 때,
                            핵식 관점 - 계좌 이체, 고개 관리 로직
                            부가 관점 - 로깅, 데이터 베이스 연결 로직

            5) 이식 가능한 서비스 추상화(PSA)
                Portable Service Abstraction : 스프링에서 제공하는 다양한 기술들을 '추상화' 해
                    개발자가 쉽게 사용하는 '인터페이스'

                    ex) 클라이언트의 매핑 / 클래스, 메서드의 매핑을 위한 에너테이션들
                    스프링에서 데이터베이스에 접근하기 윟나 기술로 JPA, MyBatis, JDBC 등
                    어떤 기술을 사용하든 일관된 방식으로 데이터베이스에 접근하도록 인터페이스를 지원
                    WAS 역시 PSA의 예시 중 하나로, 어떤 서버를 사용할더라고(톰캣, 언터로우, 제티 등)
                    코드를 동일하게 작성 가능

    3. 스프링 부트 3 둘러보기
        첫 번째 스프링 부트 3 예제 만들기
            01 단계 -
                springbootdeveloper 패키지에
                TestController.java 만들겁니다.

        스프링 부트 스타터 살펴보기

            스프링 부트 스타터는 의존성이 모여있는 그룹에 해당함.
            스타터를 사용할 경우 필요한 기능을 간편하게 설정 가능
            스타터의 명명 규칙
                spring-boot-starter-{작업유형}

            자주 사용하는 스타터의 예시
            spring-boot-starter-wdb : Spring MVC를 사용해 RESTful 웹 서비스를 개발 할 때 필요한 의존성
            spring-boot-starter-test : 스프링 애플리케이션을 테스트하기 위해 필요한 의존성 모음
            spring-boot-starter-validation : 유효성 검사를 위해 필요한 의존성 모음
            spring-boot-starter-actuator : 모니터링을 위해 애플리케이션에서 제공하는 다양한 정보를
                제공하기 쉽게 하는 의존성 모음
            spring-boot-starter-jpa : ORM(추후 수업 예정)을 사용하기 위한 인터페이스의 도움인
                JPA를 더 쉽게 사용하기 위한 의존성 모음


        스프링 부트 3와 자바 버전
            스프링 부트 3는 자바 17 버전 이후부터 사용 가능합니다!!!!

 */
@SpringBootApplication
public class SpringBootDeveloperApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootDeveloperApplication.class, args);
    }
}