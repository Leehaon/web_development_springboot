package me.ahngeunsu.springbootdeveloper.dto;

import lombok.Getter;
import me.ahngeunsu.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
    /*
        글은 제목과 내용 구성으로 이루어져 있으므로 해당 필드를 가지는 클래스를 만들었습니다
        -> ArticleResponse 클래스를 만들었다는 의미

        Entity를 argument로 받는 생성자를 추가했습니다 -> lombok으로 결판 안나기 때문에 직접 작성.
     */
}
