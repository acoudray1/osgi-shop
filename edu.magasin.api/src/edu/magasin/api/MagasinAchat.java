package edu.magasin.api;

public interface MagasinAchat {

	/**
	 * Récupération de a liste de produits (Obesrvable)
	 * @return La liste des produit du magasin
	 */
	public ListProduitObservable getListeProduits();
	
	/**
	 * Achat d'un produit
	 * @param aProduct
	 * @param quantity
	 * @throws ProductPurchaseException
	 */
	public void achatProduit(Produit aProduct, int quantity) throws ProductPurchaseException;
}
