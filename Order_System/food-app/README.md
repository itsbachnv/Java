# food-app

Spring Boot backend for the food ordering system.

## Run with Docker

This project includes Docker support for the API, MySQL, and MongoDB.

1. Copy the example environment file:

```bash
copy .env.example .env
```

2. Start the stack:

```bash
docker compose up --build
```

3. Open the API at:

```text
http://localhost:8080
```

## Services

- API: `http://localhost:8080`
- MySQL: `localhost:3307`
- MongoDB: `localhost:27017`

## Useful commands

```bash
docker compose build app
docker compose down
```

See [HELP.md](HELP.md) for the full generated project documentation.