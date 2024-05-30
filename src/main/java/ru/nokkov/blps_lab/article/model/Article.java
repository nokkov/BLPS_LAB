package ru.nokkov.blps_lab.article.model;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Article {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String author;
    private String content;

    @JsonProperty("publication_date")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime publicationDate;

    @JsonProperty("is_accepted")
    private boolean isAccepted;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "article_id")
    private List<Comment> comments;

    private int views;

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void incrementViews() {
        views++;
    }
}

