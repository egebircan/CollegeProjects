import java.sql.*;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;

public class DBOperator {

    public static boolean writeCountryToDB(ArrayList<Country> allCountries) {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Countries");

            for(Country country : allCountries) {

                PreparedStatement preparedStatement = dbConnection.prepareStatement("insert into COUNTRIES (ID, NAME, CONTINENT, CAPITAL, POPULATION) values (?, ?, ?, ?, ?)");
                preparedStatement.setString(1, country.getId().toString());
                preparedStatement.setString(2, country.getName());
                preparedStatement.setString(3, country.getContinent());
                preparedStatement.setString(4, country.getCapital());
                preparedStatement.setString(5, country.getPopulation().toString());

                preparedStatement.execute();

            }

            dbConnection.close();

        }

        catch(Exception e){

            System.out.println(e);
            e.printStackTrace();

            return false;

        }

        return true;
    }

    public static Country getCountryByID(Integer countryId) {

        String name= "";
        String continent = "";
        String capital = "";
        Integer population = 0;

        Country country = new Country(name, continent, capital, population);

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Countries");

            PreparedStatement preparedStatement =  dbConnection.prepareStatement("select * from COUNTRIES where ID = ?");
            preparedStatement.setString(1, countryId.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            name  = resultSet.getString("NAME");
            continent = resultSet.getString("CONTINENT");
            capital = resultSet.getString("CAPITAL");
            population = parseInt(resultSet.getString("POPULATION"));

            dbConnection.close();

            country.setName(name);
            country.setContinent(continent);
            country.setCapital(capital);
            country.setPopulation(population);
        }

        catch(Exception e){

            e.printStackTrace();
        }

        return country;
    }

    public static boolean deleteCountryById(Integer countryId) {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Countries");

            PreparedStatement preparedStatement =  dbConnection.prepareStatement("delete from COUNTRIES where ID = ?");
            preparedStatement.setString(1, countryId.toString());

            preparedStatement.execute();

            dbConnection.close();

        }

        catch(Exception e){

            System.out.println(e);
            e.printStackTrace();

            return false;

        }

        return true;
    }

    public static boolean updateCountryPopulationByID(Integer countryId, Integer population) {

        try {

            Class.forName("com.mysql.jdbc.Driver");

            Connection dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Countries");

            PreparedStatement preparedStatement =  dbConnection.prepareStatement("update COUNTRIES set POPULATION = ? where ID = ?");
            preparedStatement.setString(1, population.toString());
            preparedStatement.setString(2, countryId.toString());

            preparedStatement.execute();

            dbConnection.close();

        }

        catch(Exception e){

            System.out.println(e);
            e.printStackTrace();

            return false;

        }

        return true;
    }
}
