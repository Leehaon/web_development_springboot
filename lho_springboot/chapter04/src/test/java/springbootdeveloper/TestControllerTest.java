package springbootdeveloper;
/*
    Test 클래스를 만드는 방법

    1) 테스트하고자 하는 클래스(main/java 내에 있는 클래스)를 엽니다.
    2) public class 클래스명이 있는 곳에 클래스명을 클리
    3) alt + enter를 누르면 팝업이 나옵니다.
    4) create test 선택
    5) 저희 프로젝트 상으로는 JUnit5로 고정되어 있습니다.
 */
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest // 테스트용 에너테이션 컨텍스트 생성
@AutoConfigureMockMvc   // MockMvc 생성 및 자동 구성 - 목업
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    // 위에 부분은 객체 생성
    // 밑에 부분은 메서드
    @BeforeEach
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();           // builder().build()의 응용 형태
    }

    @AfterEach  // 테스트 실행 후 실행하는 메서드
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers : 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception{       // throws Exception : 예외로 처리함
        // given
        final String url = "/test";         // 앞으로 자주 나오는 방식이라 알아두시기 바랍니다.
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));
        // maybeags/web_development_java에 c15_casting에 CentralControll.java / Main.java 확인

        // when
        final ResultActions result = mockMvc.perform(get(url)       // (1)
                .accept(MediaType.APPLICATION_JSON));               // (2)

        // then
        result.andExpect(status().isOk())                           // (3)
                                                                    // (4)
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}
/*
    (1) : perform() 메서드는 요청을 전송하는 역할을 하는 메서드.
        return값으로 resultActions 객체를 받음.
        ResultActions 객체는 반환값을 검증하고 확인하는 andExpect() 메서드를 제공함.

    (2) : accept() 메서드는 요청을 보낼 때 무슨 타입으로 '응답을 받을지' 결정하는 메서드.
        저희는 주로 JSON을 이용할 예정

    (3) : andExpect() 메서드는 응답을 검증. TestController에서 만든 API는 응답으로
        OK(200)을 반환하므로 이에 해당하는 메서드인 isOK()를 사용해 응답 코드가 200(OK)인지 확인

    (4) : jsonPath("$[0].{필드명}")은 JSON 응답값의 값(value)을 가져오는 역할을 하는 메서드.
        0(인덱스)번째 배열에 들어 있는 객체의 id, name의 값을 가져오고 저장된 값과 같은지 확인
        (memberRepository.savedMember.getId()등을 이용해서)



            HTTP 주요 응답 코드

            200 OK - isOk() - HTTP 응답 코드가 200 OK인지 검증
            201 create - isCreated() - HTTP 응답 코드가 201 Created인지 검증
            400 Bad Request - isBadRequest() - HTTP 응답 코드가 400 Bad Request인지 검증
            403 Forbidden - isForbidden() - 응답 코드가 403 Forbidden인지 검증
            404 Not Found - isNotFound() - 응답 코드가 404 Not Found인지 검증
            400번대 응답 코드 - is4xxClientError() - 응답 코드가 400번대 응답 코드인지 검증
            500 Internal server Error - isInternalServerError() - 500 Internal Server Error인지 검증
            500번대 응답 코드 - is5xxServerError - 응답 코드가 500번대 응답 코드인지 검증
         다 하신 분들은
            테스트 코드 패턴을 연습하기 위해서
            src-main-java-springbootdebeloper 폴더에
            QuizController.java 만들기
 */
/*
    @SpringBootTest :
        애플리케이션 클래스에 추가하는 에너테이션인 @SpringBootApplication이
        있는 클래스를 찾고, 그 클래스에 포함돼 있는 빈을 찾은 다음,
        테스트용 애플리케이션 컨텍스트라는 것을 생성합니다.

    @AutoConfigureMockMvc :
        MockMve를 생성하고 자동으로 구성하는 에너테이션으로,
        이 것은 애플리케이션을 서버에 배포하지 않고도 테스트용 MVC 환경을 만들어
        요청, 전송, 응답 기능을 제공하는 유틸리티 클래스.
            즉, '컨트롤러를 테스트할 때 사용되는 클래스'
 */