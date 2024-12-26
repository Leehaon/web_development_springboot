import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class JUnit {
    @DisplayName("1 + 2는 3이다.")
    @Test
    public void junitTest() {
        // given : 사전준비
        int a = 1;
        int b = 2;
        // when : 테스트 실행
        int sum = 3;

        // then : 검증
        Assertions.assertEquals(sum, a + b);
    }

//    @DisplayName("1 + 3는 4이다.")
//    @Test
//    public void junitFailedTest() {
//        // given : 사전준비
//        int a = 1;
//        int b = 3;
//        // when : 테스트 실행
//        int sum = 3;            // 일부러 틀리게 했습니다.
//
//        // then : 검증
//        Assertions.assertEquals(sum, a + b);
//    }
/*
    @DisplayName 에너테이션 : 테스트 이름을 명시하는 에너테이션
    @Test 에너테이션을 붙인 메서드는 테스트를 수행하기 위한 메서드가 됨.
        JUnit은 테스트 끼리 영향을 주지 않도록 각 테스트를 실행할 때마다
        테스트를 위한 객체를 만들고, 테스트 종료 시점에 실행 객체를 소멸시킴.

    .asserEquals() 메서드 : 첫 번째 인수에는 기대하는 값,.
        두 번째 인수에는 실제로 검증할 값을 argument로 받습니다.
            -> 근데 이거 안쓸겁니다.

    test/java에 JUnitCycleTest.java
 */
}
