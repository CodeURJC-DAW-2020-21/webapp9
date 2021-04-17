package urjc.ugc.ultragamecenter.Repositories;
import urjc.ugc.ultragamecenter.Models.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository

public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findByid(long id);

}
