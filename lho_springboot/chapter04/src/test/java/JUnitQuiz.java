import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
// import static의 경우 종종 보게 될텐데 정적 메서드에 해당하는 것을 가져올 때 쓰입니다.

public class JUnitQuiz {
    /*
        문제 01.
        String으로 선언한 변수 3개가 있습니다. 여기서 세 변수 모두 null이 아니며 name1과
        name2는 같은 값을 가지고, name3는 다른 나머지 두 변수와 다른 값을 가지는 데,
        이를 검증하는 테스트를 작성해보세요.
     */
    @Test
    public void junitTest1() {
        String name1 = "홍길동";
        String name2 = "홍길동";
        String name3 = "홍길금";

        // 모든 변수가 null이 아닌지 확인하는 코드 작성할 것
        assertThat(name1).isNotNull();
        assertThat(name2).isNotNull();
        assertThat(name3).isNotNull();
        // name1과 name2가 같은지 확인
        assertThat(name1).isEqualTo(name2);
        // name1과 name3가 다른지 확인
        assertThat(name1).isNotEqualTo(name3);
    }

    @Test
    public void junitTest2() {
        /*
            문제 02.
                int로 선언된 변수 3개가 있습니다. number1, number2, number3는 각각 15, 0, -5
                세 변수가 각각 양수, 0, 음수이고, number1은 number2보다 큰 값이고,
                number3는 number2보다 작은 값임을 검증하는 테스트를 작성해보세요.
         */
        int number1 = 15;
        int number2 = 0;
        int number3 = -5;

        // 세 변수가 각각 양수, 0, 음수인지 확인하는 코드
        assertThat(number1).isPositive();
        assertThat(number2).isZero();   // 양수인지 확인하면 false / 음수인지 확인해도 false
        assertThat(number3).isNegative();
        // number1이 number2보다 큰 값인지 확인
        assertThat(number1).isGreaterThan(number2);
        // number3가 number 2보다 작은지 확인
        assertThat(number3).isLessThan(number2);
    }
}