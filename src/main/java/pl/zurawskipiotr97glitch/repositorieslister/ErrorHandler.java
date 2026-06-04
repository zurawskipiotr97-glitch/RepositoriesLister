package pl.zurawskipiotr97glitch.repositorieslister;

import tools.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
class ErrorHandler {

    private final ObjectMapper objectMapper;

    ErrorHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleNotFound(HttpClientErrorException.NotFound exception) {
        GithubErrorResponse githubErrorResponse = objectMapper.readValue(
                exception.getResponseBodyAsString(),
                GithubErrorResponse.class
        );

        return new ErrorResponse(
                exception.getStatusCode().value(),
                githubErrorResponse.message()
        );
    }

    record GithubErrorResponse(
            String message,
            String documentation_url,
            String status
    ) {
    }

    public record ErrorResponse(
            int status,
            String message
    ) {
    }
}