package teamfresh.api.application.compensation.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CompensationRepository extends CrudRepository<Compensation, Long> {

    @Query(
            "select c from Compensation c" +
                    "join c.compensation"
    )
    List<Compensation> findAllWithFetch();
}
