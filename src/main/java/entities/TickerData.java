package entities;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "ticker_data")
public class TickerData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Override
    public String toString() {
        return "TickerData{" +
                "id=" + id +
                ", time=" + time +
                ", closePrice=" + closePrice +
                ", openPrice=" + openPrice +
                ", dateEntry=" + dateEntry +
                '}';
    }

    @Column(name = "time")
    private LocalTime time;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "open_price")
    private Double openPrice;

    @ManyToOne
    @JoinColumn(name = "date_entry_id")
    private DateEntry dateEntry;

    public DateEntry getDateEntry() {
        return dateEntry;
    }

    public void setDateEntry(DateEntry dateEntry) {
        this.dateEntry = dateEntry;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double openPrice) {
        this.openPrice = openPrice;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double closePrice) {
        this.closePrice = closePrice;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}