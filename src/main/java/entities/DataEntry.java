package entities;

import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
@Table(name = "data_entry")
public class DataEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Expose
    @Column(name = "close_price")
    private Double closePrice;

    @Expose
    @Column(name = "open_price")
    private Double openPrice;

    @Expose
    @ManyToOne
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;

    @Expose
    @Column(name = "timestamp")
    private Timestamp timestamp;

    public DataEntry(Ticker ticker, Timestamp timestamp, Double closePrice, Double openPrice) {
        this.ticker = ticker;
        this.timestamp = timestamp;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
    }
}