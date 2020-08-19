package ninckblokje.poc.db.deadlock.fk.service;

import lombok.extern.slf4j.Slf4j;
import ninckblokje.poc.db.deadlock.fk.entity.Message;
import ninckblokje.poc.db.deadlock.fk.repository.MessageRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@Transactional
@Slf4j
public class MessageService {

    private final MessageRepository repository;

    public MessageService(MessageRepository repository) {
        this.repository = repository;
    }

    @Async
    public CompletableFuture<Void> deleteMessageFindByIdAsync(String id) {
        log.info("Deleting message {}", id);

        Message msg = repository.findById(id).get();
        repository.delete(msg);

        return CompletableFuture.allOf();
    }
}
