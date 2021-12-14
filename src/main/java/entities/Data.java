package entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "data")
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ticker_id")
    private Ticker ticker;

    @Column(name = "close_price")
    private Double closePrice;

    @Column(name = "open_price")
    private Double openPrice;

    @Column(name = "market_cap")
    private Long marketCap;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Long marketCap) {
        this.marketCap = marketCap;
    }

    public Double getOpenPrice() {
        return openPrice;
    }

    public void setOpenPrice(Double open_price) {
        this.openPrice = open_price;
    }

    public Double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(Double close_price) {
        this.closePrice = close_price;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}