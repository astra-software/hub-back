package com.astra.api.hub_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astra.api.hub_api.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
