package me.ahngeunsu.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/*
    domain 패키지 -> Article.java
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id // id 필드를 기본키(PK)로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키를 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)   // 'title'이라는 not null 컬럼과 매핑
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Builder            // 빌더 패턴으로 객체 생성
    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 이상의 update 메서드 정의 후에 -> 수정 요청과 관련된 DTO 작성


    /*
        @Builder 애너테이션은 롬복에서 지원하는 애너테이션으로, 생성자 위에 입력하면 빌더 패턴
        방식으로 객체 생성 가능

        객체를 유연하고 직관적으로 생성할 수 있기 때문에, 개발자들이 애용하는 '디자인 패턴'
        어느 필드에 어떤 값이 들어가는지 명시적으로 파악할 수 있습니다.

        repository pacakage 생성(domain과 같은 라인에) -> BlogRepository.java 인터페이스 생성
     */

}
