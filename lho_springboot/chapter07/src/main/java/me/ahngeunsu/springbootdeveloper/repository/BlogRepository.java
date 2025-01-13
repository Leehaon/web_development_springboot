package me.ahngeunsu.springbootdeveloper.repository;

import me.ahngeunsu.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
/*
    이상의 코드를 통해 JpaRepository 클래스를 상속 받을 때, 엔티티 Article과 PK인 Long을
    argument로 받았습니다.

    해당 리포지토리를 사용할 때, 'JpaRepository'에서 제공하는 여러 메서드 사용 가능.

    블로그 글 작성을 위한 API 구현하기
        구현 과정 :
            Service 클래스에서 method 구현
            Controller 클래스에서 사용할 method 구현,
            API를 실제로 테스트 할겁니다.

                   요청                    save()                   save()
        클라이언트 <----> 2. 컨트롤러 <-------------------> 1. 서비스 <------> 리포지토리
                   응답  (BlogController.java)          (BlogService.java)  (BlogRepository.java)
                   POST
               /api/articles

        서비스 메서드 코드 작성
            Create
            서비스 계층에서 요청을 받을 객체인 AddArticleRequest 객체를 생성하고,
            BlogService 클래스를 생성한 후에 -> 블로그 글 추가 메서드인 save()를 구현

            지시 사항

            repository 패키지와 같은 라인에 dto 패키지 생성 ->
            컨트롤러에서 요청한 본문을 받을 객체인 AddArticleRequest.java 파일을 생성

            DTO(Data Transfer Object) : 계층끼리 데이터를 교환하기 위해 사용하는 객체
            DAO는 데이터베이스와 연결되고 데이터를 조회하고 수정하는 사용되는 객체라 비교가 필요합니다.
            DAO의 경우에는 데이터 수정 관련된 로직이 포함되지만 DTO는 단순하게 데이터를 옮기기 위해 사용하는 전달자 역할
            -> 그래서 별도의 비지니스 로직이 필요하지 않다.


 */