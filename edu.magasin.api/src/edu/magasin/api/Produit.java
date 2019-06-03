package edu.magasin.api;

/**
 * Produit avec identifiant indiquant un libellé, prix et quantité disponible
 * @author rolucas
 *
 */
public class Produit {

	private int id;
	private String label;
	private double prix;
	private int quantite;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
}
