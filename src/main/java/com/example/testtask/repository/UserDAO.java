package com.example.testtask.repository;

import com.example.testtask.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    List<User> findByBirthdayBetween(LocalDate startDate, LocalDate endDate);
}
