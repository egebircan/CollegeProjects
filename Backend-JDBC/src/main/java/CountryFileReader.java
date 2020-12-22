import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import static java.lang.Integer.parseInt;

public class CountryFileReader
{

    public static ArrayList<Country> readCountriesFromFile(String filePath)
    {
        ArrayList<Country> countries = new ArrayList();

        try {

            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while(true) {

                String line = bufferedReader.readLine();
                if (line == null)
                {
                    break;
                }
                //System.out.println(line);
                String[] linesArray = line.split(",");
                String name = linesArray[0];
                String continent = linesArray[1];
                String capital = linesArray[2];
                Integer population = parseInt(linesArray[3]);

                Country country = new Country(name, continent, capital, population);

                countries.add(country);
            }

            fileReader.close();
        }

        catch (FileNotFoundException e) {

            System.out.println("no file");
            e.printStackTrace();
        }

        catch (IOException e) {

            System.out.println("no have rights to read that file");
            e.printStackTrace();

        }

        return countries;
    }

}