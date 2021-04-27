package ejemplojdbc.edu.fpdual.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import ejemplojdbc.edu.fpdual.dao.City;
import ejemplojdbc.edu.fpdual.dao.Country;

/**
 * Country DTO Manager.
 * 
 * Contains all the queries used to consult and manipulate Countries data.
 * 
 * @author jose.m.prieto.villar
 *
 */
public class CountryManager {

	/**
	 * Finds all the cities in the DB based on a list of ids.
	 * 
	 * @param con DB connection
	 * @param ids Countries id set to search for.
	 * @return a {@link List} of {@link Country}
	 */

	public Country findCountryName(Connection con, String name) {

		String sql = "select * from Country where name =?";
		try (PreparedStatement miState = con.prepareStatement(sql)) {

			miState.setString(1, name);

			ResultSet resultado = miState.executeQuery();

			Country pais = null;

			if (resultado.next()) {
				pais = new Country(resultado);

			}

			return pais;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Country> findAllCountry(Connection con) {

		String sql = "Select * from country";

		try (Statement miState = con.createStatement()) {
			ResultSet resultado = miState.executeQuery(sql);

			List<Country> paises = new ArrayList<>();

			while (resultado.next()) {

				paises.add(new Country(resultado));

			}

			return paises;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public List<Country> findAllById(Connection con, Set<String> ids) {
		// Creates the SQL command
		String sql = String.format("SELECT * FROM Country WHERE Code in (%s)",
				ids.stream().map(data -> "\"" + data + "\"").collect(Collectors.joining(", ")));

		// Create a prepared statement
		try (Statement stmt = con.createStatement()) {

			// Executes sql command
			ResultSet result = stmt.executeQuery(sql);
			// Set before first registry before going through it.
			result.beforeFirst();

			// Initializes variables
			List<Country> countries = new ArrayList<>();

			// Run through each result
			while (result.next()) {
				// Initializes a country per result
				countries.add(new Country(result));
			}

			return countries;

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	// METODO DE MIGUEL PARA EL COUNTRYLANGUAJE
//	public List<CountryLanguage> returnListOfLanguages(Connection con, ResultSet result) throws SQLException{
//		Map<String, Country> countryMap= new TreeMap<>();
//		List<CountryLanguage> languages = new ArrayList<>();
//		while(result.next()) {
//		String code=result.getString("CountryCode");
//		CountryLanguage language = new CountryLanguage(result);
//		countryMap.computeIfAbsent(code, str -> new CountryManager().findAllById(con, Collections.singleton(str)).get(0));
//		// if(countryMap.get(code)==null) {
//		// countryMap.put(code, new CountryManager().findAllById(con, Collections.singleton(code)).get(0));
//		// }
//		language.setCountry(countryMap.get(code));
//		languages.add(language);
//		}
//		return languages;
//		}
}
