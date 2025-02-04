package com.astra.api.hub_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.astra.api.hub_api.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    
}
