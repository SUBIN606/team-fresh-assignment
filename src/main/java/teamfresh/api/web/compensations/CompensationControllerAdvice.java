package teamfresh.api.web.compensations;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamfresh.api.application.compensation.exception.CompensationNotFoundException;
import teamfresh.api.web.ErrorResponse;

@RestControllerAdvice(basePackages = "teamfresh.api.web.compensations")
public class CompensationControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CompensationNotFoundException.class)
    public ErrorResponse handleCompensationNotFoundException(
            CompensationNotFoundException e
    ) {
        return new ErrorResponse(e.getMessage());
    }
}
