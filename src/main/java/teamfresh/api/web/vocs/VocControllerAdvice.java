package teamfresh.api.web.vocs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamfresh.api.application.compensation.exception.CompensationAlreadyExistException;
import teamfresh.api.application.voc.exception.VocNotFoundException;
import teamfresh.api.web.ErrorResponse;

@RestControllerAdvice(basePackages = {"teamfresh.api.web.vocs"})
public class VocControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VocNotFoundException.class)
    public ErrorResponse handleVocNotFoundException(
            VocNotFoundException e
    ) {
        return new ErrorResponse(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CompensationAlreadyExistException.class)
    public ErrorResponse handleCompensationAlreadyExistException(
            CompensationAlreadyExistException e
    ) {
        return new ErrorResponse(e.getMessage());
    }
}
