
// application
// infrastucture/UserService.java 

package com.example.app.application;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.example.app.api.exceptions.DuplicateEmailException;
import com.example.app.api.exceptions.UserNotFoundException;
import com.example.app.domain.AppUser;
import com.example.app.domain.Role;
import com.example.app.domain.RoleRepository;
import com.example.app.domain.UserRepository;

// Integrating logger
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	public UserService(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	public AppUser registerUser(String name, String email, Set<Role> roles) {
		if (userRepository.findByEmail(email).isPresent()) {
			throw new DuplicateEmailException(email);
		}
		logger.info("Registering user: {}", email);
		AppUser user = new AppUser(null, name, email, roles);
		return userRepository.save(user);
	}

	public Optional<AppUser> getUserById(Long id) {
		return userRepository.findById(id);
	}

	public List<AppUser> getAllUsers() {
		return userRepository.findAll();
	}

	public AppUser updateUser(Long id, String name, String email) {
		AppUser user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		user.setName(name);
		user.setEmail(email);
		return userRepository.save(user);
	}

	public void deleteUser(Long id) {

		AppUser user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
		userRepository.delete(user);

	}

	public AppUser assignRoleToUser(Long userId, String roleName) {

		AppUser user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		Role role = roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
		user.addRole(role);
		return userRepository.save(user);

	}

	public AppUser assignRolesToUser(Long userId, List<String> roleNames) {
		AppUser user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
		for (String roleName : roleNames) {
			Role role = roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
			user.addRole(role);
		}
		return userRepository.save(user);
	}

}
