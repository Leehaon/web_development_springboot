package me.ahngeunsu.springbootdeveloper.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {
    // 요청값(이름, 값, 만료 기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response,
                            String name,
                            String value,
                            int maxAge) {
        // import jakarta.servlet.http.Cookie;
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력 받아서 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request,
                                    HttpServletResponse response,
                                    String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            return;                 // return; -> 메서드 종료
        }

        for (Cookie cookie : cookies) {     // enhanced for loops로 작성
            if (name.equals(cookie.getName())) {
                cookie.setValue("");
                cookie.setValue("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
    }
    /*
        deleteCookie() : 쿠키 이름을 입력 받아서 쿠키를 삭제
            -> 실제로 삭제하는 방법은 없으므로 파라미터로 넘어온 카의 쿠키를
            빈값으로 바꾸고 만료 시간을 0으로 설정해서 쿠키가 재생성되자마자 만료처리를
            함으로써 구성했습니다.

            20250120에 객체를 직렬화 / 역직렬화하는 메서드를 구현할 예정
     */


}
