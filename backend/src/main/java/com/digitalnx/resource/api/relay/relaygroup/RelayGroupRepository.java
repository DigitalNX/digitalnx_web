package com.digitalnx.resource.api.relay.relaygroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RelayGroupRepository extends JpaRepository<RelayGroup, Integer> {
    @Query("SELECT id FROM #{#entityName}")
    List<Integer> getAllIds();
}
