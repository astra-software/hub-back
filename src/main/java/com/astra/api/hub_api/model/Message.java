package com.astra.api.hub_api.model;

import java.time.LocalDateTime;

import com.astra.api.hub_api.emodel.ReadStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "message")
public class Message {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "chat_id", referencedColumnName = "id", columnDefinition = "VARCHAR(255)")
  private Chat chat;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sender_user_id")
  private User senderUser;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "recipient_user_id")
  private User recipientUser;

  @Column
  private String content;

  @Column(name = "read_status")
  private ReadStatus readStatus;

  @Column
  private LocalDateTime timestamp;
}
