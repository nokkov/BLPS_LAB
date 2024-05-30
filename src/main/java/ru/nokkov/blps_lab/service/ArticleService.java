package ru.nokkov.blps_lab.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import ru.nokkov.blps_lab.article.model.Article;
import ru.nokkov.blps_lab.article.model.ArticleDTO;
import ru.nokkov.blps_lab.article.model.Comment;
import ru.nokkov.blps_lab.article.repository.ArticleRepository;

@Service
public class ArticleService {

    private final PartnerService partnerService;

    private final ArticleRepository articleRepository;

    public ArticleService(PartnerService partnerService, ArticleRepository articleRepository) {
        this.partnerService = partnerService;
        this.articleRepository = articleRepository;
    }

    @Transactional
    public void addArticle(Article article) {
        articleRepository.save(article);
        partnerService.registerArticle(article.getId(), null);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void addComment(Long articleId, Comment comment) {
        Article article = getArticle(articleId);

        if (article.isAccepted() && article.getPublicationDate() != null) {
            article.addComment(comment);
            articleRepository.save(article);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    public void deleteArticle(Long articleId) {
        articleRepository.deleteById(articleId);
    }

    // FIXME: как здесь разграничить доступ к опубликованным и неопубликованным статьям?
    public Article getArticle(Long articleId) {
        // logger.info("getArticle(): id = " + articleId);
        Article article = articleRepository.findById(articleId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Article not found"));

        if (article.isAccepted() && article.getPublicationDate() != null) {
            article.incrementViews();
            articleRepository.save(article);
        }

        return article;
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void updateArticle(Long articleId, Article updatedArticle) {
        Article article = getArticle(articleId);
        article.setTitle(updatedArticle.getTitle());
        article.setAuthor(updatedArticle.getAuthor());
        article.setContent(updatedArticle.getContent());
        articleRepository.save(article);
    }

    @Transactional
    public void acceptArticle(Long articleId) {
        Article article = getArticle(articleId);
        article.setAccepted(true);
        articleRepository.save(article);
    }

    @Transactional
    public void setPublicationDate(Long articleId, LocalDateTime publicationDate) {
        Article article = getArticle(articleId);

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(publicationDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Date");
        }

        article.setPublicationDate(publicationDate);
        articleRepository.save(article);
    }

    public List<Article> getUnacceptedArticles() {
        List<Article> allArticles = articleRepository.findAll();
        return allArticles.stream()
                .filter(article -> !article.isAccepted())
                .collect(Collectors.toList());
    }

    public List<Article> getAcceptedArticles() {
        List<Article> allArticles = articleRepository.findAll();
        return allArticles.stream()
                .filter(article -> article.isAccepted() && article.getPublicationDate() == null)
                .collect(Collectors.toList());
    }

    public List<ArticleDTO> getPublishedArticles(int page, int size, String author, String title) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Article> articles = articleRepository.getPublishedArticles(pageRequest, author, title);
        List<ArticleDTO> list = articles.stream().map(
                article -> {
                    ArticleDTO articleDTO = new ArticleDTO();
                    articleDTO.setId(article.getId());
                    articleDTO.setAuthor(article.getAuthor());
                    articleDTO.setTitle(article.getTitle());
                    return articleDTO;
                }
        ).collect(Collectors.toList());
        return list;
    }

    public List<Article> getAllArticles() {
        return articleRepository.findAll();
    }

    public void deleteAllArticles() {
        articleRepository.deleteAll();
    }

    public int getViews(Long articleId) {
        return getArticle(articleId).getViews();
    }
}
