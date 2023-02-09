package teamfresh.api.web.vocs;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import teamfresh.api.application.compensation.exception.CompensationAlreadyExistException;
import teamfresh.api.application.voc.exception.VocNotFoundException;

@RestControllerAdvice(basePackages = {"teamfresh.api.web.vocs"})
public class VocControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(VocNotFoundException.class)
    public String handleVocNotFoundException(
            VocNotFoundException e
    ) {
        return e.getMessage();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CompensationAlreadyExistException.class)
    public String handleCompensationAlreadyExistException(
            CompensationAlreadyExistException e
    ) {
        return e.getMessage();
    }
}
