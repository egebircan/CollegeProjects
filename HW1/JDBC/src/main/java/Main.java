import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {

        ArrayList<Country> allCountries = CountryFileReader.readCountriesFromFile("/home/egebircan/Desktop/world.txt");

        for(Country country : allCountries) {
            //DBOperator.writeCountryToDB(country);
        }

        //Country country = DBOperator.getCountryByID(0);

        //System.out.println(DBOperator.deleteCountryById(1));

        //System.out.println(DBOperator.updateCountryPopulationByID(3, 0));

    }
}
