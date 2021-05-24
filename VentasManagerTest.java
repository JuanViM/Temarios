package edu.gestock.persistence.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assume.assumeNotNull;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.gestock.persistence.conector.Conector;
import edu.gestock.persistence.dao.Categoria;
import edu.gestock.persistence.dao.Venta;
import javafx.collections.ObservableList;

class VentasManagerTest {

	@Test
	void testFindAllVenta() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findAll");

		Connection con = new Conector().getMySQLConnection();
		try {
			ObservableList<Venta> buy;
			buy = new VentasManager().findAllVenta(con);

			assertNotNull(buy);

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testFindVentaBynVenta() throws ClassNotFoundException, SQLException {
		System.out.println("Probando el findid");

		Connection con = new Conector().getMySQLConnection();
		try {	
		Venta aux = new VentasManager().FindVentaBynVenta(con, "gfrthg");

		assertNotNull(aux);// esta bien preguntar

		} finally {
			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testInsertVenta() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando insert");

		try {

			Venta buy = new Venta("RRPP", "stardard", 15.9, Date.valueOf(LocalDate.now()));
			int numero = new VentasManager().insertVenta(con, buy);
			Venta aux = new VentasManager().FindVentaBynVenta(con, "RRPP");
			//assertEquals(buy, aux); // preguntar
			assumeNotNull(aux);
			assertNotEquals(numero, 0);

		} finally {

			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testUpdateVenta() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("probando update");

		try {

			Venta buy = new Venta("RRPP", "stardard", 100, Date.valueOf(LocalDate.now()));
			int numero = new VentasManager().updateVenta(con, buy, "RRPP");
			Venta aux = new VentasManager().FindVentaBynVenta(con, "RRPP");
			//assertEquals(buy, aux); // preguntar
			assumeNotNull(aux);
			assertNotEquals(numero, 0);

		} finally {

			try {
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	@Test
	void testDeleteVenta() throws ClassNotFoundException, SQLException {
		Connection con = new Conector().getMySQLConnection();
		System.out.println("Probando el delete");

		try {
			
			
			int numero = new VentasManager().deleteVenta(con, "RRPP");
			Venta buy = new VentasManager().FindVentaBynVenta(con, "RRPP");
			assertNull(null, buy);
			assertNotEquals(numero, 0);

		} finally {
			try {
				con.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
