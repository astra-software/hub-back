package com.astra.api.hub_api.model;

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
@Table(name = "chat")
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "chat_id")
  private String chatId;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_sender_id")
  private User senderUser;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "user_recipient_id")
  private User recipientUser;

  @Column(name = "is_blocked")
  private boolean isBlocked = false;

  @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Message> messages = new ArrayList<>();;

}
