package ninckblokje.poc.db.deadlock.fk;

import lombok.extern.slf4j.Slf4j;
import ninckblokje.poc.db.deadlock.fk.entity.Message;
import ninckblokje.poc.db.deadlock.fk.repository.MessageRepository;
import ninckblokje.poc.db.deadlock.fk.service.MessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@SpringBootTest
@Slf4j
class PocFkDeadlockApplicationTests {

    private final int maxMessages = 5000;

    @Autowired
    private MessageRepository repository;
    @Autowired
    private MessageService service;

    @BeforeEach
    @Rollback(false)
    public void beforeEach() {
        repository.deleteAll();

        for (int i = 0; i < maxMessages; i++) {
            log.info("Creating message pmsg{}", i);
            repository.save(createMessage(i));
        }
    }

    @Test
    public void testDeleteMessageFindById() {
        List<CompletableFuture<Void>> jobs = new ArrayList<>();

        for (int i = 0; i < maxMessages; i++) {
            jobs.add(service.deleteMessageFindByIdAsync(String.format("pmsg%d", i)));
        }

        jobs.forEach(CompletableFuture::join);
    }

    Message createMessage(int i) {
        return Message.builder()
                .id(String.format("pmsg%d", i))
                .content(String.format("parent %d", i))
                .messages(
                        Arrays.asList(
                                Message.builder()
                                        .id(String.format("cmsg%d0", i))
                                        .content(String.format("child %d - 0", i))
                                        .build()
                        )
                )
                .build();
    }
}
