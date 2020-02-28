import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        ArrayList<Country> allCountries = CountryFileReader.readCountriesFromFile("/home/egebircan/Desktop/world.txt");

        //DBOperator.writeCountryToDB(allCountries);

        //DBOperator.writeCountryToDB(allCountries.get(1));

        //Country country = DBOperator.getCountryByID(197);
        //System.out.println(country.getName());

        //System.out.println(DBOperator.deleteCountryById(4));

        //System.out.println(DBOperator.updateCountryPopulationByID(5, 0));

    }
}
