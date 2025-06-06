
// domain/RoleRepository.java

package com.example.app.domain.repository;

import java.util.Optional;

import com.example.app.domain.Role;

public interface RoleRepository {

	Optional<Role> findByName(String name);

	Role save(Role role);

}
