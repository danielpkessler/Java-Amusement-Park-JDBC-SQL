/*
 * AUTEURS : DANIEL KESSLER & YASSINE BELADEL
 * FICHIER : Classe ManegeInfo TP3
 * COURS : IFT 1176
 * 
 * Le but de cette classe est de créer fenêtre secondaireavec les
 * informations (attributs) sur les manèges de la base de données.
 * La fenêtre prinicpale est réaffichée quand la fenêtre des parcs
 * est fermée.
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

// Définition de ManegeInfo qui hérite de JFrame.
public class ManegeInfo extends JFrame {

	private MainClass mainClass;

	// Constructeur de la classe
	public ManegeInfo(Manege manege, BDDGestion bdd, Connection connection, MainClass mainClass) throws SQLException {
		// Instanciation de mainClass
		this.mainClass = mainClass;

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		// Taille de la fenêtre
		setSize(600, 360);

		// Container de la fenêtre
		Container container = getContentPane();
		container.setLayout(new FlowLayout());

		// Le titre de la fenêtre sera le nom du manège choisi.
		setTitle(manege.getNom());

		// Label avec les informations du manège (au nord de la fenêtre).
		JLabel jLabel = new JLabel(manege.toString());
		setLayout(new BorderLayout());
		add(jLabel, BorderLayout.NORTH);

		JPanel panel = new JPanel();
		// Label pour indiquer dans quels parcs se trouve le manège.
		JLabel parksLabel = new JLabel("Présent dans les parcs suivants : ");
		panel.add(parksLabel);
		container.add(panel);

		/*
		 * On retoruve les emplacements où se trouve le manège avec la méthode
		 * emplacementsManege de la classe BDDGestion.
		 */
		Set<String> emplacements = bdd.emplacementsManege(manege);
		String[] data = emplacements.toArray(new String[0]);
		JList<String> list = new JList<>(data);

		JScrollPane scrollPane = new JScrollPane(list);
		panel.add(scrollPane);

		// La fenêtre va prendre la taille de son contenu.
		pack();

		// La fenêtre devient visible
		setVisible(true);

		addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosed(java.awt.event.WindowEvent windowEvent) {
				// Quand la fenêtre est fermée, la fenêtre principale est affichée
				mainClass.showMainWindow();
			}
		});

	}
}
