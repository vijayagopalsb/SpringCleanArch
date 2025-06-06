package com.example.app.infrastructure;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.domain.Role;

public interface SpringDataRoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name);
	
}
