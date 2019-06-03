package edu.magasin.api;

public interface FournisseurMagasin {

	/**
	 * Ajout d'un produit disponible
	 * @param name Libell� du produit
	 * @param price prix du produit
	 * @param count nombre de produits disponibles
	 * @throws ProductCreationException En cas d'erreur lors de la cr�ation de l'article
	 */
	public void addProduct(String name, String price, String count) throws ProductCreationException;
}
