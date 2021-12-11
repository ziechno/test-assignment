package entities;

import javax.persistence.*;

@Entity
@Table(name = "ticker")
public class Ticker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "symbol", unique = true)
    private String symbol;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "year_founded")
    private Integer year_founded;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "employee_number")
    private Integer employeeNumber;

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getYear_founded() {
        return year_founded;
    }

    public void setYear_founded(Integer year_founded) {
        this.year_founded = year_founded;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}