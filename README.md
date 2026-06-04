# Repositories Lister

Application exposes a REST endpoint that returns GitHub repositories for a given user. Forked repositories are excluded from the response.

## Technology Stack

* Java 25
* Spring Boot 4
* Gradle (Kotlin DSL)
* WireMock (integration tests)

## Running the Application

Clone the repository and run:

```bash
./gradlew bootRun
```

The application starts on:

```text
http://localhost:8080
```

## API

### Get repositories

```http
GET /{username}
```

Example:

```http
GET /octocat
```

Example response:

```json
[
  {
    "repositoryName": "Hello-World",
    "ownerLogin": "octocat",
    "branches": [
      {
        "name": "main",
        "lastCommitSha": "7fd1a60b01f91b314f59955a4e4d..."
      }
    ]
  }
]
```

### User not found

When a GitHub user does not exist, the API returns:

HTTP Status:

```text
404 Not Found
```

Response body:

```json
{
  "status": 404,
  "message": "Not Found"
}
```

## Running Tests

Run integration tests:

```bash
./gradlew test
```

## Notes

The application uses GitHub REST API v3 as a backing API.

Forked repositories are filtered out from the response.

Repository branches include branch name and last commit SHA.
