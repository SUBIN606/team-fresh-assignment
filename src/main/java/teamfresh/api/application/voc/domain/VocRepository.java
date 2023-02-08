package teamfresh.api.application.voc.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/** VOC 리포지토리 */
public interface VocRepository extends CrudRepository<Voc, Long> {

    /** VOC 목록을 fetch join을 사용하여 모두 조회합니다. */
    @Query(
            "select v from Voc v " +
                    "join fetch v.blame " +
                    "left join fetch v.compensation " +
                    "left join fetch v.compensation.penalty " +
                    "left join fetch v.compensation.penalty.objection"
    )
    List<Voc> findAllWithFetch();
}
