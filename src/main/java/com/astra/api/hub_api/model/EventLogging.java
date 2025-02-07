package com.astra.api.hub_api.model;

import java.time.LocalDateTime;

import com.astra.api.hub_api.emodel.EventLoggingDenominator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "event_logging")
public class EventLogging {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long structuredId;

  private Long userId;

  @Column(name = "logging_denominator")
  private EventLoggingDenominator loggingDenominator;

  @Column
  private String description;

  @Column
  private String reason;

  @Column
  private LocalDateTime timestamp;

}
