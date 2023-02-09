package teamfresh.api.web.penalties;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamfresh.api.application.penalty.exception.PenaltyNotFoundException;
import teamfresh.api.web.ErrorResponse;

@RestControllerAdvice(basePackages = {"teamfresh.api.web.penalties"})
public class PenaltyControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PenaltyNotFoundException.class)
    public ErrorResponse handlePenaltyNotFoundException(
            PenaltyNotFoundException e
    ) {
        return new ErrorResponse(e.getMessage());
    }
}
