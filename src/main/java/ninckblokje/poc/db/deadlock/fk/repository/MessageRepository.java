package ninckblokje.poc.db.deadlock.fk.repository;

import ninckblokje.poc.db.deadlock.fk.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, String> {
}
