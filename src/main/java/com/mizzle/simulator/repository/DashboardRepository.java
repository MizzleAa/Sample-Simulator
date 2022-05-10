package com.mizzle.simulator.repository;

import com.mizzle.simulator.entity.Dashboard;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends JpaRepository<Dashboard, Long>{
    Page<Dashboard> findAll(Pageable pageable);
    
}
