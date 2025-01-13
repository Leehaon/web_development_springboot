package me.ahngeunsu.springbootdeveloper;
/*
    서버 실행 안될 때 일단 한 번 시도해보기
    1. 햄버거 - build - rebuild project
    2. intellij 전부 다 끄고 재시작
    3. SpringBootDeveloperApplication.java 들어와서
    4. current file인거 확인하시고 재시작
 */
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
    1. 사전 지식 : 타임리프(Thymeleaf)
        타임리프는 템플릿 엔진입니다. 다만 템플릿 엔진은 HTML과 템플릿 엔진을 위한 문법을 섞어서 사용.
            즉 이제는 HTML 지식이 약간 필요합니다.

            -> 저희는 다음 과정에서 HTML을 배우게 되므로 아주 간단한 화면 구성을 위해서 코드를 작성할 예정

        * 템플릿 엔진 : 스프링 서버에서 데이터를 받아 우리가 보는 웹 페이지, 즉 HTML 상에서
            그 데이터를 넣어 보여주는 도구.

        템플릿 엔진 개념 잡기
            ↓ 간단한 템플릿 문법을 위한 예
            <h1 text=${이름}>
            <p text=${나이}>

            h1 태그에서는 ${이름}이 텍스트 어트리뷰트(속성)로 할당돼있습니다.
            p 태그도 비슷한 상황입니다.
            이상이 템플릿 문법의 예시인데,
            이렇게 해두면 서버에서 이름, 나이라는 key로 데이터를 템플릿 엔진에 넘겨주고,
            템플릿 엔진은 이를 받아 HTML에 값을 적용합니다.

            ↓ 서버에서 보내준 데이터 예
            {
                이름: "홍길동",
                나이: 11
            }
            값이 달라지면 그때그대 화면에 반영하니 동적인 웹 페이지를 만들 수 있게 되는 것입니다.

            템플릿 엔진은 각각 문법이 조금씩 다른 편입니다. 그래서 템플릿 엔진마다 문법을 새로 배워야 하는
            단점은 존재합니다만 구조는 그래도 비슷합니다.
                대표적인 템플릿 엔진 예시: JSP, 타임리프, 프리마커 등

                하지만 스프링 타임리프를 권장하고 있으므로 우리는 타임리프 사용 예정

            타임리프 표현식과 문법
                타임리프의 문법은 직관적인 편인데, 자주 사용하는 표현식과 문법은 예시로만 보여드리고,
                구체적인 사용 방법은 실습으로 알아볼겁니다.

                이하에서 소개하는 표현식들은 전달받은 데이터를 사용자들이 볼 수 있게
                뷰로 만들기 위해 사용되는 표현식 예시입니다.


            😀 타임리프 표현식
            ${...} - 변수의 값 표현식
            #{...} - 속성 파일 값 표현식
            @{...} - URL 표현식
            *{...} - 선택한 변수의 표현식. th:object에서 선택한 객체에 접근

            😀 타임리프 문법
            th:text - 텍스트를 표현할 때 사용 - th:text=${person.name}
            th:each - 컬렉션을 반복할 때 사용 - th:each="person:${persons}"
            th:if - 조건이 true인 때만 표시 - th:if"${person.age}>=20"
            th:unless - 조건이 false인 때만 표시 - th:unless"${person.age}>=20"
            th:href - 이동 경로 - th:href="@{/persons(id=${person.id})}"
            th:with - 변수 값으로 지정 - th:with="name=${person.name}"
            th:object - 선택한 객체로 지정 - th:object="${person}"
 */