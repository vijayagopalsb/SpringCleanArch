## SpringCleanArch

###  Clean Architecture Compliance

SpringCleanArch applies **Clean Architecture**

#### Principles

- **Independent of Frameworks**: Core logic is decoupled from frameworks.
- **Testable**: Business rules can be tested without UI, database, or web server.
- **Pluggable Interfaces**: UI, DB, and external interfaces are plug-ins.
- **Dependency Rule**: Source code dependencies always point inward, toward the core domain.

#### Project Structure

```bash
SpringCleanArch/
├── .mvn/                  # Maven wrapper files
├── logs/                  # Application logs
├── uploads/               # Directory for uploaded files (e.g., user photos)
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── app/
│                       ├── api/                 # REST Controllers
│                       │   ├── exceptions/      # Custom exceptions and handlers
│                       │   └── UserController.java
│                       ├── application/         # Business logic (Use Cases)
│                       │   └── UserService.java
│                       ├── domain/              # Core domain models and interfaces
│                       │   ├── AppUser.java
│                       │   ├── Role.java
│                       │   ├── repository/      # Repository interfaces
│                       │   │   ├── UserRepository.java
│                       │   │   └── RoleRepository.java
│                       ├── infrastructure/      # Implementations of interfaces
│                       │   ├── JpaUserRepository.java
│                       │   └── JpaRoleRepository.java
│                       └── SpringCleanArchApplication.java  # Main application entry point
├── .gitignore
├── mvnw
├── mvnw.cmd
├── pom.xml                # Maven project configuration
└── README.md
```

- **Entities**: `domain/`
- **Use Cases**: `application/`
- **Interfaces & Adapters**: `api/`, `infrastructure/`
- **Dependencies**: Outermost layers depend inward; no circular dependencies.

#### Spring Framework Usage

- **Dependency Injection**: Uses `@Service`, `@Repository`, constructor injection.
- **Exception Handling**: Custom exceptions and `@ControllerAdvice` for error handling.
- **Spring Data JPA**: Repositories defined as interfaces in domain, implemented in infrastructure.
- **File Upload/Download**: Uses `MultipartFile` and static resource handling.
- **Logging**: Uses SLF4J (`LoggerFactory`) throughout.

#### Testability & Extensibility

- **Testability**: Application and domain layers can be tested independently (mock repositories).
- **Extensibility**: New use cases, data sources, or APIs can be added without breaking core logic.

#### Code Quality & Readability

- Clean, well-structured, and readable code.
- Consistent and clear REST API endpoints.
- Good logging and meaningful error responses.
- Modular, idiomatic Java & Spring Boot style.

#### Improvements & Suggestions

To make the project even more impressive:
- **Add Unit Tests**: Include tests for application/domain layers (JUnit, Mockito).
- **Expand Documentation**: 
    - Add an overview of the architecture and a diagram.
    - Include sample API requests/responses.
- **DTO Layer**: Add DTOs to decouple API and domain models.
- **Validation**: Use `@Valid` for request body validation.

