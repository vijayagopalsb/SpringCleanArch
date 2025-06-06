## SpringCleanArch

---

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

