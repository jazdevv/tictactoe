# Tic-Tac-Toe Fullstack Technical Test

**Candidate:** Joel Pegueroles 

## Requirements

- **Docker** (version >= 28.3.3)
- **Docker Compose** (version >= 2.39.1)
- Git
- Optional: Node.js & npm/yarn & java 17 if you want to run frontend locally outside Docker

---

## How to Start
docker compose up 


## Project Structure

This repository is structured as a **monorepo** for simplicity and ease of review.

### Branches

- `main` – Main branch
- `feature/react-app` – React frontend features
- `feature/tic-tac-toe-basic-gameplay` – Backend features (game logic, API)

---

## ⚙️ Technical Stack

**Backend:**
- Java (Spring Boot)
- PostgreSQL database for persistence
- Stateless service architecture
- Logging for observability
- Dockerized

**Frontend:**
- React (TypeScript)
- SCSS 
- Consumes backend API
- Dockerized

**Docker:**
- Docker Compose manages all services: `db`, `server`, `app`
- Force image rebuild on frontend/backend changes with:
  ```bash
  docker compose up --build --force-recreate app server