package me.Leehaon.springbootdeveloper.repository;

import me.Leehaon.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
