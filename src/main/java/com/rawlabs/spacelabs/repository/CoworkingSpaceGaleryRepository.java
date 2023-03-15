package com.rawlabs.spacelabs.repository;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoworkingSpaceGaleryRepository extends JpaRepository<CoworkingSpace, Long> {
}
