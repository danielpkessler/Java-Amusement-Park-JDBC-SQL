/*
 * AUTEURS : DANIEL KESSLER & YASSINE BELADEL
 * FICHIER : Classe BDDGestion TP3
 * COURS : IFT 1176
 * 
 * Cette classe a pour but de gérer les liens avec une base de données.
 * Elle se connecte à la base de données, à partir d'un URL.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.*;

public class BDDGestion {
	/*
	 * L'URL, le nom d'utilisateur et le mot de passe pour se connecter à la base de
	 * données.
	 */
	private String url;
	private String login;
	private String passwd;

	// La connexion à la base de données.
	private Connection connection;

	/*
	 * Le constructeur utilise l'URL, l'utilisateur et le mot de passe. Il établit
	 * une connexion à la base de données.
	 */
	public BDDGestion(String url, String login, String passwd) {
		this.url = url;
		this.login = login;
		this.passwd = passwd;

		try {
			this.connection = DriverManager.getConnection(url, login, passwd);
		} catch (SQLException sql) {
			sql.printStackTrace();
		}
	}

	/*
	 * Méthode qui enregistre tous les manèges de la table coaster dans la base de
	 * données.
	 */
	public ResultSet getAllManege() throws SQLException {
		// Le Statement est une interface qui est une instruction SQL.
		Statement statement = connection.createStatement();
		// La méthode executeQuery exécute la requête SQL renvoie un ResultSet.
		return statement.executeQuery("SELECT nom FROM coaster");
	}

	// Méthode qui récupère tous les parcs uniques de la table Emplacement dans la
	// base de données.
	public ResultSet getAllParc() throws SQLException {
		Statement statement = connection.createStatement();
		// La méthode executeQuery exécute la requête SQL renvoie un ResultSet.
		return statement.executeQuery("SELECT DISTINCT parc FROM Emplacement");
	}

	// Méthode qui ajoute/insert un nouveau manège à la base de données.
	public void addManege(Manege manege, String parc) throws SQLException {
		// Requête SQL pour insérer un nouveau manège dans la table coaster
		String requeteInsertManege = "INSERT INTO coaster (nom, hauteur, vitesse) VALUES (?, ?, ?)";
		PreparedStatement statementAddManege = connection.prepareStatement(requeteInsertManege);

		// Définition des valeurs des paramètres dans la requête.
		statementAddManege.setString(1, manege.getNom());
		statementAddManege.setDouble(2, manege.getHauteur());
		statementAddManege.setDouble(3, manege.getVitesse());

		// Exécutetion de la requête d'insertion.
		statementAddManege.executeUpdate();

		/*
		 * Requête SQL pour insérer une insertion dans la table Emplacement" qui établit
		 * le lien entre le manège au parc.
		 */
		requeteInsertManege = "INSERT INTO Emplacement (parc, manege) VALUES (?, ?)";
		statementAddManege = connection.prepareStatement(requeteInsertManege);
		// Définition des valeurs des paramètres dans la requête.
		statementAddManege.setString(1, parc);
		statementAddManege.setString(2, manege.getNom());
		// Exécutetion la requête d'insertion.
		statementAddManege.executeUpdate();
	}

	// Méthode pour récupérer la liste des manèges dans un parc voulu.
	public List<Manege> listeDuParc(String parc) throws SQLException {
		// Requête SQL qui récupère les manèges situés dans un parc voulu.
		String requeteListeParc = "SELECT coaster.* FROM coaster JOIN Emplacement ON coaster.nom = Emplacement.manege WHERE Emplacement.parc = ?";

		PreparedStatement statementListeDuParc = connection.prepareStatement(requeteListeParc);
		// Définition la valeur du paramètre dans la requête.
		statementListeDuParc.setString(1, parc);

		// La méthode executeQuery exécute la requête SQL renvoie un ResultSet.
		ResultSet resultSet = statementListeDuParc.executeQuery();

		// Liste pour stocker tous les manèges d'un parc voulu.
		List<Manege> manegeList = new ArrayList<>();
		while (resultSet.next()) {

			// On enregistre les informations du manège de l'objet ResultSet.
			String nom = resultSet.getString("nom");
			double hauteur = resultSet.getDouble("hauteur");
			double vitesse = resultSet.getDouble("vitesse");

			// Création d'un nouvel objet manege (qui sera ajouté a la liste).
			manegeList.add(new Manege(nom, hauteur, vitesse));
		}

		// Retourne liste des manèges présents dans un parc voulu.
		return manegeList;
	}

	// Fonction pour obtenir la liste des parcs où un manège voulu est situé.
	public Set<String> emplacementsManege(Manege manege) throws SQLException {
		// Requête SQL qui enregistre les parcs dans lesquels se trouve un manège voulu.
		String requeteEmplacement = "SELECT parc FROM Emplacement WHERE manege = ?";

		PreparedStatement statementEmplacementsManege = connection.prepareStatement(requeteEmplacement);
		statementEmplacementsManege.setString(1, manege.getNom());

		// La méthode executeQuery exécute la requête SQL renvoie un ResultSet.
		ResultSet resultSet = statementEmplacementsManege.executeQuery();

		/*
		 * Set qui stock les parcs où le manège voulu se situe (le Set évite les
		 * doublons), chaque parc apparaîtra une fois seulement.
		 */
		Set<String> emplacements = new HashSet<>();
		while (resultSet.next()) {
			// Ajouter chaque nom de parc à l'ensemble.
			emplacements.add(resultSet.getString("parc"));
		}

		// Retourne l'ensemble des noms de parcs où le manège voulu est situé.
		return emplacements;
	}

	// Méthode "getter" pour obtenir un manège à partir de son nom.
	public Manege getManege(String nomManege) throws SQLException {
		// Préparer la requête SQL qui récupère les informations d'un manège à partir de
		// son nom.
		String requeteGetManege = "SELECT * FROM coaster WHERE nom = ?";

		PreparedStatement statementGetManege = connection.prepareStatement(requeteGetManege);
		statementGetManege.setString(1, nomManege);

		// La méthode executeQuery exécute la requête SQL renvoie un ResultSet.
		ResultSet resultSet = statementGetManege.executeQuery();

		if (resultSet.next()) {

			// On enregistre les informations du manège de l'objet ResultSet.
			String nom = resultSet.getString("nom");
			double hauteur = resultSet.getDouble("hauteur");
			double vitesse = resultSet.getDouble("vitesse");

			return new Manege(nom, hauteur, vitesse);
		}

		return null;
	}

	/*
	 * Fonction pour obtenir un Srting qui représente la fréquence des manèges dans
	 * chaque parc.
	 */
	public String frequence() throws SQLException {
	    // Requête SQL qui calcule la fréquence des manèges dans chaque parc.
	    String requeteFrequence = "SELECT parc, manege, COUNT(*) AS frequency FROM Emplacement GROUP BY parc, manege";

	    PreparedStatement statementFrequence = connection.prepareStatement(requeteFrequence);

	    // La méthode executeQuery exécute la requête SQL renvoie un ResultSet.
	    ResultSet resultSet = statementFrequence.executeQuery();

	    StringBuilder message = new StringBuilder();
	    int maxParcLength = 0;
	    int maxManegeLength = 0;

	    // On trouve la longueur maximale des noms de parc et manège.
	    while (resultSet.next()) {
	        String parc = resultSet.getString("parc");
	        String manege = resultSet.getString("manege");

	        if (parc.length() > maxParcLength) {
	            maxParcLength = parc.length();
	        }
	        if (manege.length() > maxManegeLength) {
	            maxManegeLength = manege.length();
	        }
	    }

	    resultSet.beforeFirst();

	    // Définition du format de l'affichage
	    String format = "%-" + (maxParcLength + 2) + "s%-" + (maxManegeLength + 2) + "s%-10s%n";
	    message.append(String.format(format, "Parc", "Manège", "Fréquence"));

	    while (resultSet.next()) {
	        String parc = resultSet.getString("parc");
	        String manege = resultSet.getString("manege");
	        int frequency = resultSet.getInt("frequency");

	        // Formater les valeurs dans un String et l'ajouter au message.
	        message.append(String.format(format, parc, manege, frequency));
	    }

	    // On retourne la fréquence des manèges dans tous les parcs.
	    return message.toString();
	}
	

	/*
	 * Surcharge de la méthode toString pour afficher les informations des manèges
	 * et des parcs.
	 */
	@Override
	public String toString() {
		StringBuilder manegeInfo = new StringBuilder();

		try {
			// On obtient tous les parcs uniques (DISTINCT) de la table Emplacement.
			String sqlParc = "SELECT DISTINCT parc FROM Emplacement";
			PreparedStatement statementParc = connection.prepareStatement(sqlParc);
			ResultSet resultSetParc = statementParc.executeQuery();

			// Pour chaque parc, obtenir les manèges qui sont présents.
			while (resultSetParc.next()) {
				String parc = resultSetParc.getString("parc");

				manegeInfo.append("Parc : ").append(parc).append("\n");

				// Requête SQL pour récupérer les manèges associés à un parc voulu.
				String sqlManege = "SELECT coaster.* FROM coaster JOIN Emplacement ON coaster.nom = Emplacement.manege WHERE Emplacement.parc = ?";
				PreparedStatement statementManege = connection.prepareStatement(sqlManege);
				statementManege.setString(1, parc);
				ResultSet resultSetManege = statementManege.executeQuery();

				while (resultSetManege.next()) {
					// Extraire les valeurs des colonnes du résultat et ajouter les informations du
					// manège à la chaîne de caractères.
					String nom = resultSetManege.getString("nom");
					double hauteur = resultSetManege.getDouble("hauteur");
					double vitesse = resultSetManege.getDouble("vitesse");

					manegeInfo.append("\tManege : ").append(nom).append(", Hauteur : ").append(hauteur)
							.append(", Vitesse : ").append(vitesse).append("\n");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// On retourne un String avec les infromations des maneges et des parcs.
		return manegeInfo.toString();
	}

	// On retourne l'objet de connexion à la base de données.
	public Connection getConnection() {
		return connection;
	}

}
