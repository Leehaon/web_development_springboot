import org.junit.jupiter.api.*;
/*
    *의 의미는 SQL문에서도 배우겠지만
    all의 뜻을 지니고 있습니다.
    즉, org.junit.jupiter.api 이하의 모든 것들을 import 하겠다는 의미
 */
public class JUnitCycleTest {

    @BeforeAll  // 전체 테스트를 시작하기 전에 1회 실행
    static void beforeAll() {
        System.out.println("@BeforeAll");
    }

    @BeforeEach // 테스트 케이스를 시작하기 전마다 실행
    public void beforeEach() {
        System.out.println("@BeforeEach");
    }

    @Test
    public void test1() {
        System.out.println("@test1");
    }

    @Test
    public void test2() {
        System.out.println("@test2");
    }

    @Test
    public void test3() {
        System.out.println("@test3");
    }

    @AfterAll  // 전체 테스트를 마치고 종료하기 전에 1회 실행
    static void afterAll() {
        System.out.println("@AfterAll");
    }

    @AfterEach
    public void afterEach() {
        System.out.println("@AfterEach");
    }
}
/*
    @BeforeAll
        전체 테스트를 시작하기 전에 처음으로 한 번만 실행
        데이터베이스에 연결해야 하거나 테스트 환경을 초기화할 때 사용
        실행 주기에서 한 번만 호출돼야 하기 때문에 메서드를 static으로 선언(정적 메서드)

    @BeforeEach
        테스트 케이스를 시작하기 전에 매번 실행
        테스트 메서드에서 사용하는 객체를 초기화하거나 테스트에 필요한 값을 미리
            넣을 때 사용할 수 있음
        각 인스턴스에 대해 메서드를 호출해야 하므로 메서드는 static이 아니어야 함.

    @AfterAll
        전체 테스트를 마치고 종료하기 전에 한 번만 실행
        데이터베이스 연결을 종료할 때나 공통적으로 사용하는 자원을 해제할 때 사용
        실행 주기에서 한 번만 호출돼야 하기 때문에 메서드를 static으로 선언(정적 메서드)

    @AfterEach
        각 테스트 케이스를 종료하기 전 매번 실행함.
        테스트 이후 특정 데이터를 삭제해야 하는 경우 사용
 */