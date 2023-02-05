package teamfresh.api.application.voc.exception;

/** VOC를 찾지 못한 경우 던짐 */
public class VocNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "VOC를 찾을 수 없습니다.";

    public VocNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public VocNotFoundException(final String message) {
        super(message);
    }

    public VocNotFoundException(final Long id) {
        super(String.format("%s에 해당하는 Voc를 찾을 수 없습니다.", id));
    }
}
