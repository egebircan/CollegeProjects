import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;
import java.util.ArrayList;

public class DBOperator {

    public static boolean writeCountryToDB(ArrayList<Country> allCountries) {

        try {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
            EntityManager entityManager = emf.createEntityManager();

            for(Country country : allCountries) {

                entityManager.getTransaction().begin();
                entityManager.persist(country);
                entityManager.getTransaction().commit();

            }

            entityManager.close();
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

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
            EntityManager entityManager = emf.createEntityManager();

            country = entityManager.find(Country.class, countryId);

            entityManager.close();

        }

        catch(Exception e){

            e.printStackTrace();
        }

        return country;
    }

    public static boolean deleteCountryById(Integer countryId) {

        try {

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
            EntityManager entityManager = emf.createEntityManager();

            Country country = entityManager.find(Country.class, countryId);

            entityManager.getTransaction().begin();
            entityManager.remove(country);
            //entityManager.createQuery("delete from COUNTRIES where ID=" + country).executeUpdate();
            entityManager.getTransaction().commit();
            entityManager.close();
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

            EntityManagerFactory emf = Persistence.createEntityManagerFactory("JPA");
            EntityManager entityManager = emf.createEntityManager();

            Country country = entityManager.find(Country.class, countryId);

            entityManager.getTransaction().begin();
            country.setPopulation(population);
            entityManager.getTransaction().commit();
            entityManager.close();
        }

        catch(Exception e){

            System.out.println(e);
            e.printStackTrace();

            return false;

        }

        return true;
    }
}

