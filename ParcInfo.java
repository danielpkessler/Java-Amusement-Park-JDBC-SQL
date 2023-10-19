/*
 * AUTEURS : DANIEL KESSLER & YASSINE BELADEL
 * FICHIER : Classe ParcInfo TP3
 * COURS : IFT 1176
 * 
 * Le but de cette classe est de créer fenêtre secondaireavec les
 * informations sur les parcs de la base de données.
 * La fenêtre prinicpale est réaffichée quand la fenêtre des manèges
 * est fermée. 
 */

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class ParcInfo extends JFrame implements ActionListener {

	private BDDGestion bdd;
	private String nomParc;
	private JLabel nomManegeLabel;
	private JLabel vitesseManegeLabel;
	private JLabel hauteurManegeLabel;
	private JLabel indiceCourant;
	private JLabel enteteManegeLabel;
	private JLabel enteteVitesseLabel;
	private JLabel enteteHauteurLabel;
	private JButton previousButton;
	private JButton nextButton;
	private JPanel centerPanel;
	private int indiceManege = 0;

	public ParcInfo(MainClass mainInstance, BDDGestion bdd, String nomParc, List<Manege> manegeList) {

		// On cache la fenêtre principale
		mainInstance.hideMainWindow();

		// On instancie la classe BDDGestion
		this.bdd = bdd;
		this.nomParc = nomParc;

		// Le titre de la fenêtre sera le nom du parc choisi
		setTitle(nomParc);

		// Taille de la fenêtre
		setSize(300, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Labels avec leur taille
		enteteManegeLabel = new JLabel("Nom du manège");
		enteteManegeLabel.setPreferredSize(new Dimension(100, 50));
		enteteVitesseLabel = new JLabel("Vitesse du manège en mph");
		enteteVitesseLabel.setPreferredSize(new Dimension(200, 50));
		enteteHauteurLabel = new JLabel("Hauteur du manège en pieds");
		enteteHauteurLabel.setPreferredSize(new Dimension(200, 50));

		nomManegeLabel = new JLabel();
		nomManegeLabel.setOpaque(false);
		nomManegeLabel.setBackground(new Color(0, 0, 0, 0));
		nomManegeLabel.setPreferredSize(new Dimension(100, 50));

		vitesseManegeLabel = new JLabel();
		vitesseManegeLabel.setOpaque(false);
		vitesseManegeLabel.setBackground(new Color(0, 0, 0, 0));
		vitesseManegeLabel.setPreferredSize(new Dimension(200, 50));

		hauteurManegeLabel = new JLabel();
		hauteurManegeLabel.setOpaque(false);
		hauteurManegeLabel.setBackground(new Color(0, 0, 0, 0));
		hauteurManegeLabel.setPreferredSize(new Dimension(200, 50));

		JPanel headerPanel = new JPanel();
		JLabel jLabel = new JLabel("Les manèges du parc " + nomParc);
		jLabel.setHorizontalAlignment(JLabel.CENTER);
		headerPanel.add(jLabel);
		add(headerPanel, BorderLayout.NORTH);

		centerPanel = new JPanel(new GridLayout(2, 3));
		centerPanel.setBackground(Color.GRAY);
		centerPanel.add(enteteManegeLabel);
		centerPanel.add(enteteVitesseLabel);
		centerPanel.add(enteteHauteurLabel);
		centerPanel.add(nomManegeLabel);
		centerPanel.add(vitesseManegeLabel);
		centerPanel.add(hauteurManegeLabel);

		add(centerPanel, BorderLayout.CENTER);

		/*
		 * On crée les boutons précédent et suivant pour passer d'un manège à un autre
		 * dans la fenêtre dún parc choisi.
		 */
		previousButton = new JButton("Précédent");
		nextButton = new JButton("Suivant");

		previousButton.addActionListener(this);
		nextButton.addActionListener(this);

		indiceCourant = new JLabel(indiceManege + 1 + " de " + manegeList.size());

		JPanel bottomPanel = new JPanel();

		// Bouton précédent désactivé
		previousButton.setEnabled(false);
		bottomPanel.add(previousButton);

		bottomPanel.add(indiceCourant);

		if (manegeList.size() == 1) {

			// Bouton suivant desactivé s'il y a un seul manège dans le parcs choisi.
			nextButton.setEnabled(false);
		}

		bottomPanel.add(nextButton);
		add(bottomPanel, BorderLayout.SOUTH);

		manInfo(manegeList);

		// La fenêtre va prendre la taille de son contenu.
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		List<Manege> manegeListe = null;
		try {

			// On obtient la liste des manèges du parc à partir de la base de données
			manegeListe = bdd.listeDuParc(nomParc);
		} catch (SQLException sql) {
			sql.printStackTrace();
		}

		if (e.getSource() == previousButton) {

			// L'indice du manège est mis à jour.
			indiceManege = (indiceManege - 1 + manegeListe.size()) % manegeListe.size();
		} else if (e.getSource() == nextButton) {

			// L'indice du manège est mis à jour.
			indiceManege = (indiceManege + 1) % manegeListe.size();
		}

		// Sion n'est pas au premier manège du parc, le bouton précédent est activé.
		if (indiceManege > 0) {
			previousButton.setEnabled(true);
		} else {
			previousButton.setEnabled(false);
		}

		if (indiceManege == manegeListe.size() - 1) {

			// Si on est au dernier manège du parc le bouton suivant est deactivé.
			nextButton.setEnabled(false);
		} else {
			nextButton.setEnabled(true);
		}

		// Les informations du manège de líndice actuel seront affichées.
		manInfo(manegeListe);
	}

	private void manInfo(List<Manege> manegeList) {

		// On affiche informations du manège actuel : nom, hauteur, vitesse
		Manege manege = manegeList.get(indiceManege);
		nomManegeLabel.setText(manege.getNom());
		vitesseManegeLabel.setText(String.valueOf(manege.getVitesse()));
		hauteurManegeLabel.setText(String.valueOf(manege.getHauteur()));

		// Indice de manèges du parc est mis à jour.
		indiceCourant.setText(indiceManege + 1 + " de " + manegeList.size());

		centerPanel.setBackground(Color.GRAY);

		centerPanel.revalidate();
		centerPanel.repaint();
	}
}
