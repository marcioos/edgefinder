# EdgeFinder

A backend for a sports betting arbitrage platform built with Kotlin and Spring Boot.

The goal of this project is to explore the design of a domain-driven, feature-oriented backend capable of aggregating odds from multiple sportsbooks, detecting arbitrage opportunities, and managing bets and portfolios.

## Tech Stack

- Kotlin
- Spring Boot
- Gradle Kotlin DSL
- PostgreSQL
- Flyway
- Docker
- JUnit 5
- Testcontainers

## Architecture

The project follows a feature-oriented package structure inspired by Clean Architecture.

```
sports/
sportsbook/
market/
outcome/
odds/
arbitrage/
bet/
portfolio/
```

Each feature is organized into its own `api`, `application`, `domain`, and `infrastructure` packages as the project evolves.

## Current Status

- ✅ Project scaffolding
- ✅ Initial domain model
- 🚧 Arbitrage engine
- 🚧 REST API
- 🚧 Persistence
- 🚧 Integration tests

## Roadmap

- Detect arbitrage opportunities across multiple sportsbooks
- Calculate optimal stake allocation
- Expose REST endpoints
- Persist betting history
- Provide portfolio and ROI statistics
- Add comprehensive unit and integration tests

## Goals

This project is intended to demonstrate:

- Domain-driven design
- Clean Architecture
- Kotlin best practices
- Spring Boot application design
- Automated testing
- REST API design
