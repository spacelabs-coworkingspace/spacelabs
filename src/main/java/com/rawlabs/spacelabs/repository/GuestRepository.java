package com.rawlabs.spacelabs.repository;

import com.rawlabs.spacelabs.domain.dao.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
}
