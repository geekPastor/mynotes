# 📝 Note App

> **Warning**: This project is intentionally over-structured for learning purposes. It’s a simple Notes app… built like it’s preparing for scale.

🚧 **Status:** In Active Development
Currently implementing UI screens, navigation, and polishing documentation.

---

## About

This project could honestly be called *“Clean Architecture Playground.”*

It’s a simple Notes application — create, edit, view, delete. Nothing revolutionary.

But instead of building it the “quick CRUD tutorial way”, I deliberately structured it using:

* Multi-module architecture
* Strict separation of concerns
* Dependency injection
* Interface-driven design
* Real-time backend integration

Why?

Because small apps are the safest place to experiment with big architecture decisions.

You probably wouldn’t need this level of structure for a basic notes app. But the goal here isn’t speed — it’s understanding trade-offs, boundaries, and scalability.

Read the code thoughtfully.
Steal what’s useful.
Ignore what’s excessive.

Don’t say: *“I did it because it was done there.”*
Architecture is contextual.

---

# Project Structure

This repository demonstrates a clean multi-module setup:

```text
:app
:data
:domain
```

### :domain

Pure business logic layer.

Contains:

* `Note` domain model
* Repository interfaces
* Use cases

Rules:

* No Android framework dependencies
* No Firebase knowledge
* Fully unit-testable
* 100% independent

This is the core of the application.

---

### :data

Infrastructure layer.

Contains:

* Repository implementations
* Firestore integration
* DTOs and mappers

This module implements the contracts defined in `domain`.

It knows about Firebase.
`domain` does not.

---

### :app

Presentation layer.

Contains:

* Jetpack Compose UI
* ViewModels (MVVM)
* Navigation
* Koin initialization
* Application entry point

This module wires everything together.

---

# Data Flow

The application follows a strict unidirectional flow:

```text
UI
 ↓
ViewModel
 ↓
UseCase
 ↓
Repository (interface)
 ↓
RepositoryImpl
 ↓
Cloud Firestore
```

No shortcuts.
No direct Firebase calls from UI.
No business logic in ViewModels.

---

# Dependency Injection

Dependency injection is handled using Koin.

Why Koin?

* Kotlin-first DSL
* Lightweight
* No code generation
* Perfect fit for modular projects

Each module exposes its own DI module:

* `domainModule`
* `dataModule`
* `appModule`

Everything is composed at application startup.

---

# Backend

The app uses:

* Firebase
* Cloud Firestore

Notes are stored in a Firestore collection and observed in real-time.

The goal here wasn’t just CRUD — it was integrating a cloud backend cleanly inside a modular architecture.

---

# Learning Goals

This project exists to:

* Practice modular architecture in Android
* Understand dependency boundaries
* Improve architectural decision-making
* Learn where abstraction helps — and where it doesn’t
* Experience the trade-offs of “clean” design

---

# What You Can Learn

* Multi-module Gradle structure
* Clean Architecture layering
* Dependency Injection setup
* Repository pattern implementation
* Real-time Firestore integration
* Compose + StateFlow state management

---

# What NOT to Learn

* Don’t over-engineer small production apps blindly
* Don’t add abstraction without a reason
* Don’t treat architectural purity as a goal
* Don’t forget that maintainability beats cleverness

This project is structured intentionally — not dogmatically.

---

# Tech Stack

### Language

* Kotlin 2.3.10

### Architecture

* Clean Architecture
* MVVM
* Multi-module Gradle setup

### Dependency Injection

* Koin 4.1.1

### Backend

* Firebase (BOM 34.9.0)
* Cloud Firestore

### UI

* Jetpack Compose
* Material 3

### Testing

* JUnit
* Espresso

---

# Future Improvements

* Authentication per user
* Offline-first architecture (Room cache)
* Feature-based modularization
* Improved error handling strategy
* CI/CD integration
* Advanced unit testing

---

# Final Thoughts

This isn’t just a Notes app.

It’s a sandbox for architectural experimentation.

The goal wasn’t to build fast.
The goal was to build intentionally.
