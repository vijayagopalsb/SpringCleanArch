
// domain/RoleRepository.java

package com.example.app.domain;

import java.util.Optional;

public interface RoleRepository {

	Optional<Role> findByName(String name);

	Role save(Role role);

}
