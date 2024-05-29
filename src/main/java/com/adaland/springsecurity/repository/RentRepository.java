package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.dao.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RentRepository extends JpaRepository<Rent, Long> {
    Optional<Rent> findById(long rentId);

    @Query("SELECT r FROM Rent r WHERE r.user.id = :userId")
    List<Rent> findAllByUserId(@Param("userId") long userId);
}
