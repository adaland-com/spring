package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.dao.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {
    Optional<Rent> findById(String rentId);

    @Query("SELECT r FROM Rent r WHERE r.user.id = :userId")
    List<Rent> findAllByUserId(@Param("userId") Long userId);
}
