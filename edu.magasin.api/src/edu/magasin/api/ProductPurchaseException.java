package edu.magasin.api;

/**
 * Exception retournée lors d'une erreur d'achat de produit
 * @author rolucas
 *
 */
public class ProductPurchaseException extends Exception {

	public ProductPurchaseException(String string) {
		super(string);
	}

}
