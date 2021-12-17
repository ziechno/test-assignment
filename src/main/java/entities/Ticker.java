package entities;

import com.google.gson.annotations.Expose;

import javax.persistence.*;

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

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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