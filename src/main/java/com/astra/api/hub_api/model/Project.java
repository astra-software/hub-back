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
@Table(name = "project")
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @Column(nullable = false)
  private String title;

  @Column
  private String description;

  @OneToMany(mappedBy = "project",  fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Image> imageList;

  @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL )
  private List<Util> utils;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "project_category_link",
              joinColumns = {@JoinColumn(name = "id_project")},
              inverseJoinColumns = {@JoinColumn(name = "id_category")})
  private List<Category> categories = new ArrayList<>();

  @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL )
  private List<Feedback> feedbacks;

  @Column(name = "favorite_counter")
  private Long favoriteCounter;

  @Column
  private LocalDateTime timestamp;

}