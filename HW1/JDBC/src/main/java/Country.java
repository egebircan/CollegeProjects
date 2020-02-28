public class Country {

    private String name;
    private String continent;
    private String capital;
    private Integer population;

    public Country(String name, String continent, String capital, Integer population ) {
        this.name = name;
        this.continent = continent;
        this.capital = capital;
        this.population = population;
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
