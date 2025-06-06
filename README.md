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
springcleanarch/
├── domain/
│   ├── model/
│   └── repository/
├── application/
│   ├── service/
│   └── dto/
├── infrastructure/
│   ├── repository/
│   └── controller/
└── SpringCleanArchApplication.java
```

