package entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import utils.JSONObjectMapper;
import utils.Utilities;

import javax.persistence.*;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
@Table(name = "ticker")
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Expose
    @Column(name = "symbol", unique = true)
    private String symbol;

    @Expose
    @Column(name = "full_name")
    private String fullName;

    @Expose
    @Column(name = "state")
    private String state;

    @Expose
    @Column(name = "city")
    private String city;

    @Expose
    @Column(name = "year_founded")
    private Integer yearFounded;

    @Expose
    @Column(name = "employee_number")
    private Integer employeeNumber;

    @Expose
    @Column(name = "market_cap")
    private Long marketCap;

    public Ticker(String symbol, String quoteSummary, String quoteResponse) throws JsonProcessingException {
        this.symbol = symbol;
        this.fullName = JSONObjectMapper.getObjectByKey(quoteResponse, "longName").asText();
        this.state = JSONObjectMapper.getObjectByKey(quoteSummary, "state").asText();
        this.city = JSONObjectMapper.getObjectByKey(quoteSummary, "city").asText();
        Long yearFounded = JSONObjectMapper.getObjectByKey(quoteResponse, "firstTradeDateMilliseconds").asLong();
        this.yearFounded = Utilities.millisecondToDate(yearFounded).getYear();
        this.employeeNumber = JSONObjectMapper.getObjectByKey(quoteSummary, "fullTimeEmployees").asInt();
        this.marketCap = JSONObjectMapper.getObjectByKey(quoteResponse, "marketCap").asLong();
    }
}