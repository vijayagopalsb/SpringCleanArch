
// infrastructure/JpaUserRepository.java

package com.example.app.infrastructure;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.app.domain.AppUser;
import com.example.app.domain.UserRepository;

@Repository
public class JpaUserRepository implements UserRepository {

	private final SpringDataUserRepository springDataUserRepository;

	public JpaUserRepository(SpringDataUserRepository springDataUserRepository) {
		super();
		this.springDataUserRepository = springDataUserRepository;
	}

	@Override
	public AppUser save(AppUser user) {
		return springDataUserRepository.save(user);
	}

	@Override
	public Optional<AppUser> findById(Long id) {
		return springDataUserRepository.findById(id);
	}

	@Override
	public List<AppUser> findAll() {

		return springDataUserRepository.findAll();
	}

	@Override
	public void delete(AppUser user) {
		springDataUserRepository.delete(user);
	}

	@Override
	public Optional<AppUser> findByEmail(String email) {
		return springDataUserRepository.findByEmail(email);
	}

}
