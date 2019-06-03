package edu.magasin.api;

public interface ClientMagasin {

	/**
	 * R�cuperation de la liste des produits disponibles (fait appel au Magasin)
	 * @return
	 */
	public ListProduitObservable getListeProduits();
	
	/**
	 * Achat d'un produit
	 * @param aProduit produit achett�
	 * @param quantity quantit� souhait�e
	 * @throws ProductPurchaseException En cas d'erreur lors de l'achat
	 */
	public void addAchat(Produit aProduit, int quantity) throws ProductPurchaseException;
	
	/**
	 * R�cup�ration du montant total des achat d'un client
	 * @return Montant des achats
	 */
	public double getMontantAchats();
	
	/**
	 * R�cup�ration du nom du client
	 * @return
	 */
	public String getName();
}
