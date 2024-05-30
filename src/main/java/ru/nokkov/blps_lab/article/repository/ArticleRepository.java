package ru.nokkov.blps_lab.article.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nokkov.blps_lab.article.model.Article;


@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    @Query(value = "SELECT * FROM Article WHERE (:title IS NULL OR title = :title) " +
            "AND (:author IS NULL OR author = :author) " +
            "AND publication_date IS NOT NULL " +
            "AND is_accepted",
            countQuery = "SELECT count(*) FROM Article WHERE (:title IS NULL OR title = :title) " +
                    "AND (:author IS NULL OR author = :author)",
            nativeQuery = true)
    Page<Article> getPublishedArticles(Pageable pageable, @Param("author") String author, @Param("title") String title);

}
