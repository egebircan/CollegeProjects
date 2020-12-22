import java.util.concurrent.atomic.AtomicInteger;

public class Country {
    private static final AtomicInteger count = new AtomicInteger(0);

    private Integer id;
    private String name;
    private String continent;
    private String capital;
    private Integer population;

    public Country(String name, String continent, String capital, Integer population ) {
        this.name = name;
        this.continent = continent;
        this.capital = capital;
        this.population = population;
        id = count.incrementAndGet();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
