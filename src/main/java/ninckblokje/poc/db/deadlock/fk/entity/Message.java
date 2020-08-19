package ninckblokje.poc.db.deadlock.fk.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "MSG"
)
public class Message {

    @Id
    @Column(name = "ID", updatable = false, nullable = false, length = 64)
    private String id;

    @Column(name = "CONTENT", nullable = false)
    private String content;

    @JoinTable(
            name = "MSG_LINKS",
            joinColumns = @JoinColumn(name = "PARENT_MSG_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_MSG_ID"),
            indexes = {
                    @Index(columnList = "PARENT_MSG_ID"),
                    @Index(columnList = "CHILD_MSG_ID")
            }
    )
    @OneToMany(cascade = CascadeType.ALL)
    @Builder.Default
    private List<Message> messages = new ArrayList<>();
}
