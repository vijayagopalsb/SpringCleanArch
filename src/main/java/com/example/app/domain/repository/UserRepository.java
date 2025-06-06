
// UserRepository.java interface
// domain/UserRepository.java

package com.example.app.domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.app.domain.AppUser;

public interface UserRepository {
	
	AppUser save(AppUser user);
	Optional<AppUser> findById(Long id);
	List<AppUser> findAll();
	void delete(AppUser user);
	Optional<AppUser> findByEmail(String email);

}
