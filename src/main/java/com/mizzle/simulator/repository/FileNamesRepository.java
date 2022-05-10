package com.mizzle.simulator.repository;


import java.util.List;
import java.util.Optional;

import com.mizzle.simulator.entity.Dashboard;
import com.mizzle.simulator.entity.FileNames;
import com.mizzle.simulator.payload.mapping.FileNamesMapping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileNamesRepository extends JpaRepository<FileNames, Long>{
    List<FileNamesMapping> findByDashboard(Dashboard dashboard);
    void deleteAllByDashboard(Dashboard dashboard);   
    Optional<FileNames> findByName(String name);
}
