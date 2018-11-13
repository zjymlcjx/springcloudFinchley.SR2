package com.haobai.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.haobai.entity.User;

public interface TestRepository extends JpaRepository<User, Integer>{

}
