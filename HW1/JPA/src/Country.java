import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.concurrent.atomic.AtomicInteger;

@Entity
@Table(name = "COUNTRIES")
public class Country {
    private static final AtomicInteger count = new AtomicInteger(0);

    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CONTINENT")
    private String continent;
    @Column(name = "CAPITAL")
    private String capital;
    @Column(name = "POPULATION")
    private Integer population;

    public Country() {
        super();
    }

    public Country(String name, String continent, String capital, Integer population ) {
        this.name = name;
        this.continent = continent;
        this.capital = capital;
        this.population = population;
        id = count.incrementAndGet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }
}

