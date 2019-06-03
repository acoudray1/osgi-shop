package edu.magasin.api;

public interface ClientMagasin {

	/**
	 * Récuperation de la liste des produits disponibles (fait appel au Magasin)
	 * @return
	 */
	public ListProduitObservable getListeProduits();
	
	/**
	 * Achat d'un produit
	 * @param aProduit produit achetté
	 * @param quantity quantité souhaitée
	 * @throws ProductPurchaseException En cas d'erreur lors de l'achat
	 */
	public void addAchat(Produit aProduit, int quantity) throws ProductPurchaseException;
	
	/**
	 * Récupération du montant total des achat d'un client
	 * @return Montant des achats
	 */
	public double getMontantAchats();
	
	/**
	 * Récupération du nom du client
	 * @return
	 */
	public String getName();
}
