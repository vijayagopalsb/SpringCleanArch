
// api/UserController.java

package com.example.app.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.app.api.exceptions.UserNotFoundException;
import com.example.app.application.UserService;
import com.example.app.domain.AppUser;

import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping
	public AppUser createUser(@RequestBody AppUser user) {
		logger.info("Received request to create users with email: {}", user.getEmail());
		return userService.registerUser(user.getName(), user.getEmail(), user.getRoles());
	}

	@GetMapping("/{id}")
	public AppUser getUser(@PathVariable Long id) {
		logger.info("Fetching user with id: {}", id);
		return userService.getUserById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	@PutMapping("/{id}")
	public AppUser updateUser(@PathVariable Long id, @RequestBody AppUser update) {
		return userService.updateUser(id, update.getName(), update.getEmail());
	}

	@GetMapping
	public List<AppUser> getAllUsers() {
		return userService.getAllUsers();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable Long id, HttpServletRequest request) {
		userService.deleteUser(id);
		Map<String, Object> body = new HashMap<>();
		body.put("path", request.getRequestURI());
		body.put("message", "User deleted successfully");
		body.put("timestamp", java.time.ZonedDateTime.now());
		body.put("status", 200);
		return ResponseEntity.ok(body);

	}

	@PostMapping("/{id}/roles")
	public AppUser assignRole(@PathVariable Long id, @RequestBody Map<String, String> request) {
		String roleName = request.get("role");
		return userService.assignRoleToUser(id, roleName);
	}

	@PostMapping("/{id}/roles/bulk")
	public AppUser assignRoles(@PathVariable Long id, @RequestBody Map<String, List<String>> request) {

		List<String> roleNames = request.get("roles");
		return userService.assignRolesToUser(id, roleNames);

	}

	@PostMapping("/{id}/photo")
	public ResponseEntity<Map<String, String>> uploadPhoto(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		String uploadDir = "uploads/";
		String fileName = id + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
		Path filePath = Paths.get(uploadDir, fileName);

		try {
			Files.createDirectories(filePath.getParent());
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException ioException) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Photo upload failed"));
		}

		userService.updateUserPhoto(id, fileName);

		// Return the file name/path as response
		return ResponseEntity.ok(Map.of("message", "Photo uploaded successfully", "photoPath", fileName));
	}

	@GetMapping("/photos/{filename:.+}") // allows for extensions (like .jpg, .png)
	public ResponseEntity<Resource> getPhoto(@PathVariable String filename) {

		logger.info("Received request to fetch photo: {}", filename);

		try {
			String uploadDir = System.getProperty("user.dir") + "/uploads/";
			Path filePath = Paths.get(uploadDir, filename).normalize();

			if (logger.isInfoEnabled()) {
				logger.info("Looking for photo at: {}", filePath);
			}

			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}

			// Optional: Dynamically determine content type
			String contentType = Files.probeContentType(filePath);
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);

		} catch (Exception exception) {
			logger.error("Error serving photo {}: {}", filename, exception.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}

	}
}
