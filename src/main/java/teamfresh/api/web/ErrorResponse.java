package teamfresh.api.web;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ErrorResponse {

    private String message;
    private List<FieldError> errors;

    public ErrorResponse(final String message) {
        this.message = message;
        this.errors = Collections.emptyList();
    }

    public ErrorResponse(final String message, final List<FieldError> errors) {
        this.message = message;
        this.errors = errors;
    }

    public ErrorResponse(BindingResult bindingResult) {
        this.message = "";
        this.errors = bindingResult.getFieldErrors()
                .stream()
                .map(error ->
                        new FieldError(error.getDefaultMessage(), error.getField())
                ).collect(Collectors.toList());
    }

    @Getter
    @AllArgsConstructor
    public static class FieldError{
        private String message;
        private String field;
    }
}
