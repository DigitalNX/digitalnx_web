package com.digitalnx.resource.api.layout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DashboardItemRepository extends JpaRepository<DashboardItem, Integer> {
    @Query("SELECT id FROM #{#entityName}")
    List<Integer> getAllIds();
}