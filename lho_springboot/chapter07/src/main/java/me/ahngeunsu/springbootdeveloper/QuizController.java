package me.ahngeunsu.springbootdeveloper;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController             // 우리는 매우 자주 사용할 예정입니다.
public class QuizController {

    @GetMapping("/quiz")            // (1)
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("Created!");
            case 2:
                return ResponseEntity.badRequest().body("Bad Request!");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }

    @PostMapping("/quiz")       // (2)
    public ResponseEntity<String> quiz2(@RequestBody Code code) {

        switch (code.value()) {
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }
}

record Code(int value) {}   //  (3)

/*
    QuizControllerTest.java 파일을 만들어보세요. 힌트 : 어쩌고저쩌고 한다음에 alt + enter 눌러야 함.
 */
