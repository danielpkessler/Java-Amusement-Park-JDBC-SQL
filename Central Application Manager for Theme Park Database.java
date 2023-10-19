/*
 * AUTEURS : DANIEL KESSLER & YASSINE BELADEL
 * FICHIER : Classe MainClass TP3
 * COURS : IFT 1176
 * 
 * Cette classe est la classe principale du code, elle contient 
 * la méthode "main".
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MainClass {
	private JFrame mainWindow;

	// Méthode pour afficher la fenêtre principale
	public void showMainWindow() {
		mainWindow.setVisible(true);
	}

	/*
	 * Méthode pour cacher la fenêtre principale (lors de l'ouverture des fenêtres
	 * secondaires).
	 */
	public void hideMainWindow() {
		mainWindow.setVisible(false);
	}

	public static void main(String[] args) throws SQLException {

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				// On crée d'une fenêtre avec un titre
				JFrame titre = new JFrame("Base de données -  Maneges & Parcs");
				titre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				titre.setSize(400, 300);

				// Création de listes pour stocker les noms des manèges et des parcs.
				ArrayList<String> nomsManeges = new ArrayList<>();
				ArrayList<String> nomsParcs = new ArrayList<>();

				try {
					// On établit une connexion à la base de données et on exécute une requête SQL.
					Connection connexion = DriverManager.getConnection(UserData.url, UserData.login, UserData.passwd);
					Statement statement = connexion.createStatement();
					ResultSet maneges = statement.executeQuery("SELECT * FROM coaster");

					// On remplit la liste des manèges
					while (maneges.next()) {
						nomsManeges.add(maneges.getString("nom"));
					}

					// Requête SQL pour obtenir les noms de tous les parcs différents.
					ResultSet parcs = statement.executeQuery("SELECT DISTINCT parc FROM Emplacement");
					while (parcs.next()) {
						nomsParcs.add(parcs.getString("parc"));
					}

					// Fermeture de la connexion à la base de données.
					statement.close();
					connexion.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}

				// Création de JLabels et JComboBoxes des manèges et les parcs.
				JLabel manege = new JLabel("Maneges");
				JComboBox<String> manegeComboBox = new JComboBox<>(nomsManeges.toArray(new String[0]));
				JLabel parc = new JLabel("Parcs");
				JComboBox<String> parcComboBox = new JComboBox<>(nomsParcs.toArray(new String[0]));

				// JPanel qui contiendra les JLabels et JComboBoxes.
				JPanel centerPanel = new JPanel(new GridLayout(2, 2));

				// Composants du panel.
				centerPanel.add(parc);
				centerPanel.add(manege);
				centerPanel.add(parcComboBox);
				centerPanel.add(manegeComboBox);

				// Le panel sera situé au centre de la fenêtre.
				titre.getContentPane().add(centerPanel, BorderLayout.CENTER);

				// JLabel pour afficher nos noms & ajout à la fenêtre principale.
				JLabel nom = new JLabel("TP 3 - Daniel Kessler & Yassine Beladel");
				titre.getContentPane().add(nom, BorderLayout.NORTH);

				// Menu de la fenêtre
				JMenuBar menuBar = new JMenuBar();
				JMenu menu = new JMenu("Menu");

				// ActionListener pour les boutons Quitter
				ActionListener quitAction = new ActionListener() {
					public void actionPerformed(ActionEvent e1) {
						try {
							// La connexion à la base de données est fermée.
							BDDGestion bdd = new BDDGestion(UserData.url, UserData.login, UserData.passwd);
							if (bdd.getConnection() != null) {
								bdd.getConnection().close();
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}

						// Fermeture de la fenêtre principale et fin de l'application.
						titre.dispose();
						System.exit(0);
					}
				};

				// Quitter l'application.
				JMenuItem Quitter = new JMenuItem("Quitter");
				Quitter.addActionListener(quitAction);

				// Ajout du bouton Quitter en bas (SUD) de la fenêtre
				JPanel southPanel = new JPanel(new GridBagLayout());
				JButton quitButton = new JButton("Quitter");
				quitButton.addActionListener(quitAction);
				southPanel.add(quitButton, new GridBagConstraints());

				titre.add(southPanel, BorderLayout.SOUTH);

				final MainClass mainInstance = new MainClass();

				mainInstance.mainWindow = titre;

				// ActionListener pour la JComboBox des manèges.
				manegeComboBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e1) {

						// Affichage d'informations sur le manège voulu.
						String choix = (String) manegeComboBox.getSelectedItem();
						try {
							BDDGestion bdd = new BDDGestion(UserData.url, UserData.login, UserData.passwd);
							Manege manege = bdd.getManege(choix);
							if (manege != null) {

								// Nouvelle fenêtre qui affiche les informations sur le manège voulu.
								titre.setVisible(false); // La fenêtre principale sera cachée.
								Connection conn = DriverManager.getConnection(UserData.url, UserData.login,
										UserData.passwd);
								ManegeInfo manegeInfoWindow = new ManegeInfo(manege, bdd, conn, mainInstance);
								manegeInfoWindow.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e1) {
										mainInstance.showMainWindow();
									}
								});
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});

				// ActionListener pour la JComboBox des parcs.
				parcComboBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e1) {

						// Affichage d'informations sur le parc voulu.
						String parc = (String) parcComboBox.getSelectedItem();
						try {
							BDDGestion bdd = new BDDGestion(UserData.url, UserData.login, UserData.passwd);
							List<Manege> manegeList = bdd.listeDuParc(parc);
							if (!manegeList.isEmpty()) {

								// Nouvelle fenêtre qui affiche les informations sur le parc voulu.
								titre.setVisible(false); // La fenêtre principale sera cachée.
								ParcInfo parcInfoWindow = new ParcInfo(mainInstance, bdd, parc, manegeList);
								parcInfoWindow.addWindowListener(new WindowAdapter() {
									@Override
									public void windowClosed(WindowEvent e1) {
										mainInstance.showMainWindow();
									}
								});
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});

				// Élément dans le menu pour afficher la table de fréquence des manèges.
				JMenuItem afficherTableItem = new JMenuItem("Afficher la table de fréquence d'un manège");
				afficherTableItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e1) {
						StringBuilder message = new StringBuilder();
						message.append("NB.\t\t    NOM DU MANEGE ET DES PARCS OU IL SE SITUE\n");

						try {

							// On établit une connexion à la base de données et on exécute une requête SQL.
							Connection connexion = DriverManager.getConnection(UserData.url, UserData.login,
									UserData.passwd);
							Statement statement = connexion.createStatement();
							ResultSet resultset = statement.executeQuery(
									"SELECT manege, COUNT(*) as frequency FROM Emplacement GROUP BY manege");

							while (resultset.next()) {
								String manege = resultset.getString("manege");
								int frequence = resultset.getInt("frequency");

								// Requête pour obtenir les parcs où un manège est présent.
								PreparedStatement parksStmt = connexion
										.prepareStatement("SELECT DISTINCT parc FROM Emplacement WHERE manege = ?");
								parksStmt.setString(1, manege);
								ResultSet parksRs = parksStmt.executeQuery();

								// On ajout les parcs à une liste.
								ArrayList<String> parksList = new ArrayList<String>();
								while (parksRs.next()) {
									parksList.add(parksRs.getString("parc"));
								}
								String parks = String.join(", ", parksList);

								// Informations du manège dans le message.
								message.append(frequence).append("    :    ").append(manege).append("  -  ").append(parks)
										.append("\n");

								parksRs.close();
								parksStmt.close();
							}

							// Fermeture de resultset, statement et connexion.
							resultset.close();
							statement.close();
							connexion.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}

						// Affichage du message dans une boîte de MessageDialog.
						JOptionPane.showMessageDialog(null, message.toString());
					}
				});
				menu.add(afficherTableItem);

				// Ajout de de quitter au menu.
				menu.add(Quitter);

				// Ajout du menu à la barre de menu.
				menuBar.add(menu);

				// Ajout de la barre de menu à la fenêtre principale.
				titre.setJMenuBar(menuBar);

				// Permet que la fenêtre devienne visible.
				titre.setVisible(true);
			}

		});
	}
}
