import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        ArrayList<Country> allCountries = CountryFileReader.readCountriesFromFile("/home/egebircan/Desktop/world.txt");

        //DBOperator.writeCountryToDB(allCountries);

        //Country country = DBOperator.getCountryByID(3);
        //System.out.println(country.getName());

        //System.out.println(DBOperator.deleteCountryById(1));

        //System.out.println(DBOperator.updateCountryPopulationByID(3, 31));

    }
}
