package com.astra.api.hub_api.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.astra.api.hub_api.emodel.FeedbackType;
import com.astra.api.hub_api.emodel.StructureDenominator;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "feedback")
public class Feedback  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id")
    private Article article;
    
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "project_id")
    // private Project project;

    
    @Column(name = "structure_denominator", nullable = false)
    private StructureDenominator structureDenominator;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "feedback_label_link",
                joinColumns = {@JoinColumn(name = "id_feedback")},
                inverseJoinColumns = {@JoinColumn(name = "id_label")})
    private List<FeedbackLabel> labels = new ArrayList<>();

    @Column(nullable = false)
    private FeedbackType type;

    @Column
    private String content;

    @Column(nullable = false)
    private LocalDateTime timestamp;
}
