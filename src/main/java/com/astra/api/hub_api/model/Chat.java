package com.astra.api.hub_api.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
  @Column(columnDefinition = "VARCHAR(255)", unique = true)
  private String id;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "sender_id")
  private User senderUser;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "recipient_id")
  private User recipientUser;

  @Column
  private boolean blocked = false;

  @OneToMany(mappedBy = "chat", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
  private List<Message> messages = new ArrayList<>();

}
