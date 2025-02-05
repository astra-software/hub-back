package com.astra.api.hub_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astra.api.hub_api.model.FeedbackLabel;

@Repository
public interface FeedbackLabelRepository extends JpaRepository<FeedbackLabel, Long> {
    
}
