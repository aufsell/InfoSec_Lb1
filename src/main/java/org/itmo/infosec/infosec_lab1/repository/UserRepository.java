package org.itmo.infosec.infosec_lab1.repository;


import org.itmo.infosec.infosec_lab1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
