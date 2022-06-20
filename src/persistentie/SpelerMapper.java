package persistentie;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domein.Speler;

public class SpelerMapper {

	private static final String INSERT_SPELER = "INSERT INTO ID372831_DatabaseG56.Speler (naam,geboorteJaar,speelKansen)"
			+ " VALUES (?,?,?)";
	private static final String UPDATE_SPELER = "UPDATE ID372831_DatabaseG56.Speler SET speelKansen = ? WHERE geboorteJaar = ? AND naam = ?";

	public void voegToe(Speler speler) {
		try (Connection con = DriverManager.getConnection(Connectie.DB_URL);
				PreparedStatement query = con.prepareStatement(INSERT_SPELER)) {
			query.setString(1, speler.getNaam());
			query.setInt(2, speler.getGeboorteJaar());
			query.setInt(3, speler.getSpeelkans());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}

	public Speler geefSpeler(String naam, int geboorteJaar) {
		Speler speler = null;

		try (Connection con = DriverManager.getConnection(Connectie.DB_URL);
				PreparedStatement query = con.prepareStatement(
						"SELECT * FROM ID372831_DatabaseG56.Speler WHERE naam=? AND geboorteJaar=?")) {
			query.setString(1, naam);
			query.setInt(2, geboorteJaar);
			try (ResultSet rs = query.executeQuery()) {
				if (rs.next()) {
					int speelkans = rs.getInt("speelKansen");

					speler = new Speler(naam, geboorteJaar, speelkans);
				}
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return speler;
	}

	public List<Speler> geefLijst() {
		List<Speler> lijst = new ArrayList<>();

		try (Connection con = DriverManager.getConnection(Connectie.DB_URL);
				PreparedStatement query = con.prepareStatement("SELECT * FROM ID372831_DatabaseG56.Speler");
				ResultSet rs = query.executeQuery()) {

			while (rs.next()) {
				String naam = rs.getString("naam");
				int geboorteJaar = rs.getInt("geboortejaar");
				int speelkans = rs.getInt("speelKansen");

				lijst.add(new Speler(naam, geboorteJaar, speelkans));
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}

		return lijst;
	}

	public void slaSpeelkansOp(Speler speler) {
		try (Connection con = DriverManager.getConnection(Connectie.DB_URL);
				PreparedStatement query = con.prepareStatement(UPDATE_SPELER)) {
			query.setInt(1, speler.getSpeelkans());
			query.setInt(2, speler.getGeboorteJaar());
			query.setString(3, speler.getNaam());
			query.executeUpdate();
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		}
	}
}
