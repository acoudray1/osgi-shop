package edu.magasin.api;

public interface FournisseurMagasin {

	/**
	 * Ajout d'un produit disponible
	 * @param name Libellé du produit
	 * @param price prix du produit
	 * @param count nombre de produits disponibles
	 * @throws ProductCreationException En cas d'erreur lors de la création de l'article
	 */
	public void addProduct(String name, String price, String count) throws ProductCreationException;
}
