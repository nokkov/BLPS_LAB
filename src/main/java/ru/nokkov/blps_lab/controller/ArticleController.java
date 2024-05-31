package ru.nokkov.blps_lab.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import ru.nokkov.blps_lab.article.model.Article;
import ru.nokkov.blps_lab.article.model.ArticleDTO;
import ru.nokkov.blps_lab.article.model.Comment;
import ru.nokkov.blps_lab.service.ArticleService;

@RestController
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping(value = "/create-article", consumes = "application/json", produces = "application/json")
    public void createArticle(@RequestBody Article article) {
        articleService.addArticle(article);
    }

    @GetMapping(value = "/articles/{id}", produces = "application/json")
    public Article getArticle(@PathVariable Long id) {
        return articleService.getArticle(id);
    }

    @PostMapping(value = "/add-comment/{id}", consumes = "application/json")
    public void addComment(@PathVariable Long id, @RequestBody Comment comment) {
        articleService.addComment(id, comment);
    }

    @PutMapping(value = "/articles/{id}")
    public void updateArticle(@PathVariable Long id, @RequestBody Article updatedArticle) {
        articleService.updateArticle(id, updatedArticle);
    }

    @DeleteMapping(value = "/article/{id}", consumes = "application/json")
    public void deleteArticle(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }

    @PatchMapping(value = "/articles/{id}/accept")
    public void acceptArticle(@PathVariable Long id) {
        articleService.acceptArticle(id);
    }

    @PatchMapping(value = "/articles/{id}/publish")
    public void publicateArticle(@PathVariable Long id, @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime date) {
        articleService.setPublicationDate(id, date);
    }

    @GetMapping(value = "/articles")
    public List<ArticleDTO> getPublishedArticles(
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "5") @Min(1) @Max(10) int size,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String title) {
        return articleService.getPublishedArticles(page, size, author, title);
    }

    @DeleteMapping(value = "/all-articles")
    public void deleteAllArticles() {
        articleService.deleteAllArticles();
    }

    @GetMapping(value = "/articles/{id}/views")
    public int getViews(@PathVariable Long id) {
        return articleService.getViews(id);
    }
}
