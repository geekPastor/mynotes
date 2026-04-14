# 📝 Note App

> ⚠️ **Disclaimer**
> This project is intentionally over-structured for learning purposes.
> It is a simple Notes application designed as an architecture playground rather than a production-ready product.

🚧 **Status:** Active Development
Currently improving UI polish, real-time synchronization, and architecture experiments.

---

# 📖 Overview

This project is a **Clean Architecture playground built around a simple Notes app**.

At its core, the application allows:

* Creating notes
* Editing notes
* Viewing notes
* Deleting notes
* Marking favorites
* Archiving notes
* Real-time synchronization via Firebase

However, the real objective is not CRUD functionality.

It is to explore how far architectural structure can be pushed in a small project.

---

## 🧠 Why this project exists

Most tutorials typically follow this pattern:

```text
UI → Firebase → Done
```

This project deliberately avoids that shortcut.

Instead, it explores:

* Clear separation of concerns
* Dependency inversion
* Modular architecture boundaries
* Interface-driven design
* Real-time backend integration
* Scalability trade-offs

Small apps are the safest environment to experiment with architecture decisions that would be risky in production systems.

---

# 🏗️ Architecture

This project follows **Clean Architecture + MVVM + Multi-module structure**.

---

## 📦 Modules Overview

```text
:app       → Presentation Layer (UI, ViewModels, Navigation)
:data      → Data Layer (Firebase, repositories, DTOs)
:domain    → Business Logic Layer (models, contracts, use cases)
```

---

## 🧠 :domain (Core Layer)

The **pure business logic layer**.

### Contains:

* Domain models (`Note`, `User`)
* Repository interfaces
* (Optional) Use cases

### Rules:

* ❌ No Android dependencies
* ❌ No Firebase knowledge
* ✅ Fully testable
* ✅ Independent of infrastructure

👉 This is the **core of the system**

---

## 🔌 :data (Infrastructure Layer)

Handles external systems and persistence.

### Contains:

* Firebase Firestore implementation
* DTOs and mappers
* Repository implementations

### Responsibilities:

* Implements domain contracts
* Handles data persistence and networking
* Maps DTO ↔ Domain models

👉 This layer is **replaceable (Firebase could be swapped)**

---

## 🎨 :app (Presentation Layer)

Handles everything related to UI and user interaction.

### Contains:

* Jetpack Compose UI
* MVVM ViewModels
* Navigation (Navigation 3)
* Dependency Injection (Koin)
* Application entry point

### Responsibilities:

* State management
* UI rendering
* User interactions

👉 This is the **composition layer that wires everything together**

---

# 🔄 Data Flow

The project follows a strict unidirectional flow:

```text
UI
 ↓
ViewModel
 ↓
UseCase (optional)
 ↓
Repository (interface)
 ↓
RepositoryImpl
 ↓
Firebase Firestore
```

### Rules:

* ❌ No Firebase calls in UI
* ❌ No business logic in ViewModels
* ❌ No infrastructure logic in domain

---

# 🔥 Backend

The app uses:

* Firebase Authentication
* Cloud Firestore (Realtime updates)

---

## 📂 Data structure

```text
users/{userId}/notes/{noteId}
```

---

## 🧩 Note capabilities

Each note supports:

* Favorite state
* Archive state
* Real-time updates
* Timestamp tracking (created / updated)

---

# 💉 Dependency Injection

Dependency injection is handled using **Koin 4.x**

### Why Koin?

* Kotlin-first DSL
* Lightweight
* No annotation processing
* Ideal for modular projects

---

## 📦 DI structure

Each module provides its own DI definitions:

* `domainModule`
* `dataModule`
* `appModule`

They are composed at application startup.

---

# 📦 Gradle Architecture Insight

This project enforces architectural boundaries at build level.

---

## :domain

* No Android plugin
* Pure Kotlin module
* No external dependencies on infrastructure

---

## :data

* Depends only on `:domain`
* Implements Firebase integration

---

## :app

* Depends on `:domain` + `:data`
* Entry point of the system

---

### 🚧 Why this matters

This structure prevents:

* Circular dependencies
* Firebase leakage into UI
* Business logic inside wrong layers
* Tight coupling between modules

---

# 📚 Tech Stack

## Language

* Kotlin 2.3.10

## UI

* Jetpack Compose
* Material 3

## Architecture

* Clean Architecture
* MVVM
* Multi-module Gradle setup

## Dependency Injection

* Koin 4.1.1

## Backend

* Firebase Auth
* Cloud Firestore (Realtime)

## Navigation

* Navigation 3 (experimental)

---

# 🚀 Future Improvements

* Offline-first support (Room caching layer)
* Feature-based modularization
* Better error handling strategy (`Result` wrapper)
* CI/CD pipeline (GitHub Actions)
* Advanced testing strategy (unit + integration)
* Performance optimizations for large datasets

---

# 🤝 Contribution

Contributions are welcome — especially if they improve architecture clarity or simplicity.

### How to contribute

1. Fork the repository
2. Create a feature branch:

   ```bash
   git checkout -b feature/my-feature
   ```
3. Commit changes:

   ```bash
   git commit -m "Add: my feature description"
   ```
4. Push branch:

   ```bash
   git push origin feature/my-feature
   ```
5. Open a Pull Request

---

### Contribution guidelines

* Keep architecture consistent with existing structure
* Avoid unnecessary abstraction
* Prefer simplicity over over-engineering
* Explain non-trivial decisions in PR description

---

# 🧠 Final Thought

This project is not about building a Notes app.

It is about understanding a deeper question:

> “How far can we push structure before it becomes unnecessary complexity?”

And more importantly:

> Knowing when to stop.
