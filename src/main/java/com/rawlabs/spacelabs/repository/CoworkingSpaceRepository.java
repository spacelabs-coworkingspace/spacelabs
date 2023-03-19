package com.rawlabs.spacelabs.repository;

import com.rawlabs.spacelabs.domain.dao.CoworkingSpace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoworkingSpaceRepository extends JpaRepository<CoworkingSpace, Long> {

    List<CoworkingSpace> findCoworkingSpaceByAddressIgnoreCase(String address);
    List<CoworkingSpace> findCoworkingSpaceByNameIgnoreCase(String name);

    List<CoworkingSpace> findCoworkingSpaceByNameAndByAddressIgnoreCase(String name, String address);
}
