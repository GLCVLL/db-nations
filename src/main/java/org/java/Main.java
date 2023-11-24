package org.java;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

	    public static void main(String[] args) {

	        final String jdbcUrl = "jdbc:mysql://localhost:3306/db-nation";
	        final String username = "root";
	        final String password = "root";

	        try {
	            Class.forName("com.mysql.cj.jdbc.Driver");

	            try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
	                Scanner in = new Scanner(System.in);
	                System.out.print("Inserisci una stringa di ricerca: ");
	                String searchQuery = in.nextLine();

	                String query = "SELECT " +
	                        "countries.country_id AS id, " +
	                        "countries.name AS name, " +
	                        "regions.name AS region, " +
	                        "continents.name AS continent " +
	                        "FROM " +
	                        "countries " +
	                        "JOIN " +
	                        "regions ON countries.region_id = regions.region_id " +
	                        "JOIN " +
	                        "continents ON regions.continent_id = continents.continent_id " +
	                        "WHERE " +
	                        "countries.name LIKE ? " +
	                        "ORDER BY " +
	                        "countries.name";

	                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	                    preparedStatement.setString(1, "%" + searchQuery + "%");

	                    try (ResultSet resultSet = preparedStatement.executeQuery()) {

	                        if (!resultSet.isBeforeFirst()) {
	                            System.out.println("Nessuna corrispondenza trovata nel database per: " + searchQuery);
	                        } else {
	                            while (resultSet.next()) {
	                                final int countryId = resultSet.getInt(1);
	                                final String countryName = resultSet.getString(2);
	                                final String regionName = resultSet.getString(3);
	                                final String continentName = resultSet.getString(4);

	                                System.out.printf("ID: %d, Name: %s, Region: %s, Continent: %s%n",
	                                        countryId, countryName, regionName, continentName);
	                            }
	                        }
	                    }
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}   


