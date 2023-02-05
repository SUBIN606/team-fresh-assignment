package teamfresh.api.application.compensation.exception;

public class CompensationNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "배상 정보를 찾을 수 없습니다.";

    public CompensationNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public CompensationNotFoundException(final Long id) {
        super(String.format("%s에 해당하는 배상 정보를 찾을 수 없습니다.", id));
    }
}
