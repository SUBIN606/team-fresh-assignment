package teamfresh.api.application.penalty.exception;

/** 페널티 정보를 찾지 못한 경우 던짐 */
public class PenaltyNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "페널티 정보를 찾을 수 없습니다.";

    public PenaltyNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public PenaltyNotFoundException(final Long id) {
        super(String.format("%s에 해당하는 페널티 정보를 찾을 수 없습니다.", id));
    }
}
