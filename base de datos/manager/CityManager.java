package ejemplojdbc.edu.fpdual.manager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Locale.IsoCountryCode;
import java.util.stream.Collectors;

import ejemplojdbc.edu.fpdual.dao.City;
import ejemplojdbc.edu.fpdual.dao.Country;

/**
 * City DTO Manager.
 * 
 * Contains all the queries used to consult and manipulate Cities data.
 * 
 * @author jose.m.prieto.villar
 *
 */
public class CityManager {

	/**
	 * Finds all the cities in the DB
	 * 
	 * @param con DB connection
	 * @return a {@link List} of {@link City}
	 */

	public City findid(Connection con, int id) {
		String sql = "Select * from city where id = ?";
		try (PreparedStatement pr = con.prepareStatement(sql)) {

			pr.setInt(1, id);
			ResultSet resultado = pr.executeQuery();

			City ciudad = null;

			if (resultado.next()) {
				ciudad = new City(resultado);

			}

			return ciudad;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<City> findAll(Connection con) {
		// Create general statement
		try (Statement stmt = con.createStatement()) {
			// Queries the DB
			ResultSet result = stmt.executeQuery("SELECT * FROM City");
			// Set before first registry before going through it.
			result.beforeFirst();

			// Initializes variables
			List<City> cities = new ArrayList<>();
			Map<Integer, String> countries = new HashMap();

			// Run through each result
			while (result.next()) {
				// Initializes a city per result
				cities.add(new City(result));
				// Groups the countried by city
				countries.put(result.getInt("ID"), result.getString("CountryCode"));
			}

			// Fills the country of each city
			fillCountries(con, countries, cities);

			return cities;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Fills all the countries for each city.
	 * 
	 * @param con       the Db connection
	 * @param countries the map of cities and countries.
	 * @param cities    the list of cities to update.
	 */

	private void fillCountries(Connection con, Map<Integer, String> countries, List<City> cities) {
		// Obtains all the country codes to search
		Set<String> countryCodes = new HashSet<>(countries.values());

		// Looks for all countries and groups them by id.
		Map<String, Country> countriesMap = new HashMap<>();

		List<Country> countriesFound = new CountryManager().findAllById(con, countryCodes);

		for (Country country : countriesFound) {
			countriesMap.put(country.getId(), country);
		}

		// Associates the corresponding Country to each City

		for (City city : cities) {
			String countryCode = countries.get(city.getId());
			Country foundCountry = countriesMap.get(countryCode);
			city.setCountry(foundCountry);
		}

	}
//	
//	private void fillCountries(Connection con, Map<Integer, String> countries, List<City> cities) {
//		//Obtains all the country codes to search
//		Set<String> countryCodes = new HashSet<>(countries.values());
//
//		//Looks for all countries and groups them by id.
//		Map<String, Country> countriesMap = new CountryManager().findAllById(con, countryCodes).stream()
//				.collect(Collectors.toMap(Country::getId, data -> data));
//
//		//Associates the corresponding Country to each City
//		cities.forEach(city -> {
//			String countryCode = countries.get(city.getId());
//			Country foundCountry = countriesMap.get(countryCode);
//			city.setCountry(foundCountry);
//		});
//
//	}

	public List<City> findId(Connection con, int id) {
		// creamos la variable String que va a almacenar la sentencia sql
		String consulta = ("select * from city where id=?");
		// creamos el preparedStatement bajo el nombre miprepared y es igual a con.etc y
		// le metemos la consulta
		try (PreparedStatement miprepared = con.prepareStatement(consulta)) {
			// aqui le establecemos los parametros de consulta
			miprepared.setInt(1, id);

			// ejecutamos la sentencia mediante el result set para construir la tabla de
			// datos
			ResultSet resultado = miprepared.executeQuery();

			List<City> ciudad = new ArrayList<>();
			Map<Integer, String> paises = new HashMap();

			// le decimos que coja el resultado y lo meta en un objeto city llamado ciudad
			if (resultado.next()) {
				ciudad.add(new City(resultado));
				paises.put(resultado.getInt("ID"), resultado.getString("CountryCode"));

			}
			fillCountries(con, paises, ciudad);

			// devuelve los datos de ciudad
			return ciudad;

		} catch (SQLException e) {
			// TODO: handle exception
			return null;
		}

	}

	public City FindName(Connection con, String name) {

		String sql = ("Select * from city where name =?");

		try (PreparedStatement miState = con.prepareStatement(sql)) {

			miState.setString(1, name);

			ResultSet result = miState.executeQuery();

			City ciudad = null;

			if (result.next()) {

				ciudad = new City(result);
			}
			return ciudad;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<City> findNameWordBegins(Connection con, String name) {

		String sql = "select * from city where name like ?";
		try (PreparedStatement miState = con.prepareStatement(sql)) {

			miState.setString(1, name + '%');

			ResultSet resultado = miState.executeQuery();
			resultado.beforeFirst();

			List<City> ciudad = new ArrayList<>();
			Map<Integer, String> paises = new HashMap();

			while (resultado.next()) {
				ciudad.add(new City(resultado));
				paises.put(resultado.getInt("ID"), resultado.getString("CountryCode"));
			}
			fillCountries(con, paises, ciudad);
			return ciudad;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public void updateCity(Connection con, int id, BigDecimal population) {

		String sql = ("UPDATE CITY SET POPULATION = ? WHERE id = ?");

		try (PreparedStatement miState = con.prepareStatement(sql)) {

			miState.setBigDecimal(1, population);
			miState.setInt(2, id);
			miState.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void insertCity(Connection con, String name, String Country, String district, BigDecimal population) {
		String sql = ("insert into city (id,Name,CountryCode,District,Population) values ((select max(ciudad.id)+1 from city as ciudad),?,?,?,?)");

		try (PreparedStatement miState = con.prepareStatement(sql)) {

			miState.setString(1, name);
			miState.setString(2, Country);
			miState.setString(3, district);
			miState.setBigDecimal(4, population);
			miState.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteCity(Connection con, int id) {
		String sql = ("delete from city where id = ?");

		try (PreparedStatement miState = con.prepareStatement(sql)) {

			miState.setInt(1, id);
			miState.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
