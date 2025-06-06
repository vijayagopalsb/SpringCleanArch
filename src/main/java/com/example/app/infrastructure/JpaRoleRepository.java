
// infrastructure/JpaRoleRepository.java

package com.example.app.infrastructure;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.domain.Role;
import com.example.app.domain.RoleRepository;

@Repository
public class JpaRoleRepository implements RoleRepository {

	private final SpringDataRoleRepository springDataRoleRepository;
	
	public JpaRoleRepository(SpringDataRoleRepository springDataRoleRepository) {
		this.springDataRoleRepository = springDataRoleRepository;
	}
	
	@Override
	public Optional<Role> findByName(String name){
		return springDataRoleRepository.findByName(name);
	}
	
	@Override
	public Role save(Role role) {
		return springDataRoleRepository.save(role);
	}
}
