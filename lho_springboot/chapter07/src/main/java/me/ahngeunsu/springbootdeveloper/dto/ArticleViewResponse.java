package me.ahngeunsu.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.ahngeunsu.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
    }
    //다 하신 분들은 BlogViewController.java로 넘어가세요
}
