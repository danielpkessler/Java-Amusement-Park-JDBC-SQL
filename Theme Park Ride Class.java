/*
 * AUTEURS : DANIEL KESSLER & YASSINE BELADEL
 * FICHIER : Classe Manege TP3
 * COURS : IFT 1176
 * 
 * La classe manege a pour but de définit les attributs d'un manège.
 * On implémente la classe comparable pour pouvoir comparer des
 * manèges entre eux.
 */


public class Manege implements Comparable<Manege> {
	private String nom;
	private double vitesse, hauteur;

	// pour les recherches
	public Manege(String nom) {
		this.nom = nom;
	}

	public Manege(String nom, double haut, double vite) {
		this(nom);
		hauteur = haut;
		vitesse = vite;
	}

	@Override
	public int compareTo(Manege o) {

		return nom.compareTo(o.nom);
	}

	public int hashCode() {
		return nom.hashCode();
	}

	public boolean equals(Object o) {
		if (o instanceof Manege) {
			String lenom = ((Manege) o).nom;
			return nom.equalsIgnoreCase(lenom);
		} else
			return false;
	}

	public String toString() {
		return nom + ", " + hauteur + "' , " + vitesse + " mph ";
	}

	public String getNom() {
		return nom;
	}

	public double getHauteur() {
		return hauteur;
	}

	public double getVitesse() {
		return vitesse;
	}

}
