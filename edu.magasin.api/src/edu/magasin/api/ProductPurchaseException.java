package edu.magasin.api;

/**
 * Exception retourn�e lors d'une erreur d'achat de produit
 * @author rolucas
 *
 */
public class ProductPurchaseException extends Exception {

	public ProductPurchaseException(String string) {
		super(string);
	}

}
