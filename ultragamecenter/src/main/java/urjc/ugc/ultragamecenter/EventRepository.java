package urjc.ugc.ultragamecenter;


import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Integer> {
    Event findByid(int id);
}
