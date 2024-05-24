package com.adaland.springsecurity.repository;

import com.adaland.springsecurity.model.auth.User;
import com.adaland.springsecurity.model.dao.Rent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentRepository extends JpaRepository<Rent, Long> {
    Optional<Rent> findById(String rentId);
}
