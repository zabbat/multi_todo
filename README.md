# 📱 **Todo Android App**

A modern **Android Kotlin** app built with **Jetpack Compose**, **Firebase Authentication**, and modular feature-based architecture. This project connects to a companion **Spring Boot** backend API, which handles auth validation, data persistence, and business logic.

👉 [See Spring Boot backend README](./backend/README.md) for API and server setup.

---

## Table of Contents

1. [Features](#features)
2. [Project Architecture](#project-architecture)
3. [Tech Stack](#tech-stack)
4. [Getting Started](#getting-started)
      ▪ [Running the Android App](#running-the-android-app)
      ▪ [Connecting to the Backend](#connecting-to-the-backend)
5. [Testing](#testing)
6. [Contributing](#contributing)
7. [License](#license)

---

## Features

* ✨ **Jetpack Compose UI** – Fully declarative, theme-aware interface.
* 🔐 **Firebase Auth Integration** – Handles user authentication with Google / email.
* 🌐 **Modular Retrofit API client** – Built-in auth interceptors for JWT token injection.
* 🧱 **Feature-based Architecture** – Clean separation using `data`, `domain`, `presentation`, and `di` layers.
* 🧪 **Testable with MockK + Koin** – Unit and instrumentation tests supported.
* 🧩 **Scalable Project Structure** – Easy to extend with new features under `features/`.

---

## Project Architecture

```text
features/
└── auth/
    ├── data/remote/            # Retrofit API calls + interceptors
    ├── data/firebase/          # Firebase-specific auth logic
    ├── domain/                 # Interfaces, use cases
    ├── presentation/           # Compose UI
    ├── di/                     # Module-specific Koin bindings
    └── repositories/           # Interface implementations
```

> Common patterns: MVVM with a clean architecture twist. Compose state flows from ViewModel → UI.

---

## Tech Stack

| Area      | Technology                                |
| --------- | ----------------------------------------- |
| Language  | Kotlin 2.0                                |
| UI        | Jetpack Compose, Material3                |
| Auth      | Firebase Auth SDK                         |
| Network   | Retrofit, OkHttp, Kotlinx Serialization   |
| DI        | Koin                                      |
| Testing   | JUnit 5, MockK, Espresso, Compose Testing |
| Dev Tools | Android Studio Hedgehog                   |

---

## Getting Started

### Running the Android App

1. Open **Android Studio** → *Open Existing Project* → select the root folder.
2. Set up Firebase Auth:

   * Go to the Firebase console and create a project
   * Add Android app and download `google-services.json` → place in `app/`.
3. In `local.properties`, set your backend base URL:

```properties
API_BASE_URL=http://10.0.2.2:8080
```

4. Sync Gradle and run the app on an emulator or device.

---

### Connecting to the Backend

The app communicates with a Spring Boot backend (see [`backend/`](./backend/) for setup). It expects:

* An API endpoint at `API_BASE_URL`
* Valid Firebase ID tokens from the user

Use `AuthInterceptorProviderImpl` and `RetrofitAuthInterceptor` to inject tokens automatically into headers.

> Emulator: use `10.0.2.2` to reach `localhost` on the host machine.

---

## Testing

```bash
# Unit tests
./gradlew testDebugUnitTest

# Instrumented tests (requires emulator)
./gradlew connectedDebugAndroidTest
```

Use `MockK` and `koin-test` to isolate dependencies.

---

## Contributing

1. Fork & clone this repo
2. Create a feature branch: `git checkout -b feature/my-feature`
3. Make changes and commit: `git commit -am "Add feature"`
4. Push and open a Pull Request

Conventional commit style preferred: `feat:`, `fix:`, `docs:` etc.

---

## License

This project is open source and available under the **MIT License**.

---

## Acknowledgements

* [Jetpack Compose](https://developer.android.com/jetpack/compose)
* [Firebase Auth](https://firebase.google.com/docs/auth)
* [Koin DI](https://insert-koin.io)
* [MockK](https://mockk.io)
