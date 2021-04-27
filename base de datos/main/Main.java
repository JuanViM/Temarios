package ejemplojdbc.edu.fpdual.main;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import ejemplojdbc.edu.fpdual.conector.Conector;
import ejemplojdbc.edu.fpdual.dao.City;
import ejemplojdbc.edu.fpdual.dao.Country;
import ejemplojdbc.edu.fpdual.manager.CityManager;
import ejemplojdbc.edu.fpdual.manager.CountryManager;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		//Connects to the DB
		Connection con =  new Conector().getMySQLConnection();
		try {
			//Looks for all the cities in the DB and prints them.
			//new CityManager().findAll(con).forEach(city ->System.out.println(city));
			
			//Buscamos por id
		
			//System.out.println(new CityManager().findId(con, 345));
			
			//Buscamos por nombres
			
			new CityManager().findNameWordBegins(con, "T").forEach(ciu ->System.out.println(ciu));
			//System.out.println(new CityManager().findNameWordBegins(con, "S"));
			
			//actualizamos la base de datos 
//			System.out.println(new CityManager().findId(con,1));
//			new CityManager().updateCity(con,1, BigDecimal.valueOf(70000));
//			System.out.println(new CityManager().findId(con,1));
			
			// INSERTAMOS UNA NUEVA CIUDAD
			
//			new CityManager().insertCity(con,"Vikings","RUS", "Rusia",BigDecimal.valueOf(6));
			
			//BORRAMOS UNA CIUDAD
			
			//new CityManager().deleteCity(con, 4092);
			
			//IMPRIMIMOS LOS PAISES
			
			//new CountryManager().findAllCountry(con).forEach(pai ->System.out.println(pai));;
			
			//Buscamos un pais por nombre
			
			//System.out.println(new CountryManager().findCountryName(con,"A"));
			
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
