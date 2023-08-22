package com.sabatini.microfinanciamento_coletivo.repository;

import com.sabatini.microfinanciamento_coletivo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
