package teamfresh.api.application.compensation.exception;

/** VOC 건에 이미 배상 정보가 등록된 경우 던짐 */
public class CompensationAlreadyExistException extends RuntimeException {
    public CompensationAlreadyExistException() {
        super("이미 존재하는 배상정보가 있습니다.");
    }
}
