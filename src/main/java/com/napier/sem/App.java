package com.napier.sem;

import java.sql.*;
import java.util.ArrayList;

/**
 * App class
 *
 * Class containing functions to produce reports
 */
public class App
{
    /**
     * Main method for app
     */
    public static void main(String[] args)
    {
        // Create new Application
        App a = new App();

        // Connect to database
        a.connect();

        // Get city
        City kabul = a.getCity(1);
        // Display results
        a.displayCity(kabul);

        // Disconnect from database
        a.disconnect();
    }

    /**
     * Connection to MySQL database.
     */
    private Connection con = null;

    /**
     * Connect to the MySQL database.
     */
    public void connect()
    {
        try
        {
            // Load Database driver
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Could not load SQL driver");
            System.exit(-1);
        }

        int retries = 10;
        for (int i = 0; i < retries; ++i)
        {
            System.out.println("Connecting to database...");
            try
            {
                // Wait a bit for db to start
                Thread.sleep(30000);
                // Connect to database
                con = DriverManager.getConnection("jdbc:mysql://db:3306/world?useSSL=false", "root", "example");
                System.out.println("Successfully connected");
                break;
            }
            catch (SQLException sqle)
            {
                System.out.println("Failed to connect to database attempt " + Integer.toString(i));
                System.out.println(sqle.getMessage());
            }
            catch (InterruptedException ie)
            {
                System.out.println("Thread interrupted? Should not happen.");
            }
        }
    }

    /**
     * Disconnect from the MySQL database.
     */
    public void disconnect()
    {
        if (con != null)
        {
            try
            {
                // Close connection
                con.close();
            }
            catch (Exception e)
            {
                System.out.println("Error closing connection to database");
            }
        }
    }

    /**
     * Gets a city from the database with the ID given
     * @param ID the ID of the city to be fetched
     * @return city object, based on the city
     */
    public City getCity(int ID) {
        try
        {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT * "
                            + "FROM city "
                            + "WHERE ID = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next())
            {
                City city = new City();
                city.ID = rset.getInt("ID");
                city.Name = rset.getString("Name");
                city.CountryCode = rset.getString("CountryCode");
                city.District = rset.getString("District");
                city.Population = rset.getInt("Population");
                return city;
            }
            else
                return null;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("Failed to get city details");
            return null;
        }
    }

    /**
     * Method returns information stored by a city
     * object, based on the city object provided to the
     * method
     * @param city the city to be displayed
     */
    public void displayCity(City city) {
        if(city != null) {
            System.out.println("ID: "+city.ID+"\n"+
                               "Name: "+city.Name+"\n"+
                               "CountryCode: "+city.CountryCode+"\n"+
                               "District: "+city.District+"\n"+
                               "Population: "+city.Population+"\n");
        }
    }

    /**
     * Displays the code, name, continent, region,
     * population and capital city of all countries given
     * @param countries the countries given
     */
    public void displayCountries(ArrayList<Country> countries) {
        // Check countries is not null
        if(countries == null)
        {
            System.out.println("No countries");
            return;
        }

        // Print header
        String header_format = "%-4s %-15s %-15s %-15s %-10s %-10s";
        String headers = String.format(header_format,"Code","Name","Continent","Region","Population","Capital City");
        System.out.println(headers);

        // Loop through countries and print
        for(Country country : countries) {
            // Check country is not null
            if(country == null)
                continue;

            // Print country
            String record_format = "%-4s %-15s %-15s %-15s %-10d %-10s";
            String country_string =
                    String.format(record_format, country.code,country.name,country.continent, country.region, country.population, country.capital_name);
            System.out.println(country_string);
        }
    }

    /**
     * Gets all countries organised by largest
     * population to smallest
     * @return List of countries organised from largest to smallest population
     */
    public ArrayList<Country> getAllCountries() {
        return null;
    }

    /**
     * Gets all countries in a continent organised
     * from largest population to smallest
     * @param continent the continent the countries belong to
     * @return List of countries in a continent
     */
    public ArrayList<Country> getAllCountriesInContinent(String continent) {
        return null;
    }

    /**
     * Gets all countries in a region organised
     * from largest population to smallest
     * @param region the region the countries are in
     * @return List of countries in a region
     */
    public ArrayList<Country> getAllCountriesInRegion(String region) {
        return null;
    }
}