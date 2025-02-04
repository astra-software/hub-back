package com.astra.api.hub_api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "article")
public class Article{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String title;

    @Column(name="markdown_content")
    private String markdownContent;

    @Column(name = "is_private")
    private boolean isPrivate = false;

    @Column(name = "favourite_counter")
    private Long favouriteCounter;

    @OneToMany(mappedBy = "article", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Feedback> feedbacks;

    @Column
    private LocalDateTime timestamp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "article_category_link",
                joinColumns = {@JoinColumn(name = "id_article")},
                inverseJoinColumns = {@JoinColumn(name = "id_category")})
    private List<Category> categories = new ArrayList<>();

}