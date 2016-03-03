package cat.udl.eps.entsoftarch.thesismarket.domain;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * Created by http://rhizomik.net/~roberto/
 */
@Entity
public abstract class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    ZonedDateTime dateTime;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public ZonedDateTime getDateTime() { return dateTime; }

    public void setDateTime(ZonedDateTime dateTime) { this.dateTime = dateTime; }
}
