package edu.magasin.api;

/**
 * Exception retournée lors de la creation d'un produit en erreur
 * @author rolucas
 *
 */
public class ProductCreationException extends Exception {

	public ProductCreationException(String string) {
		super(string);
	}

}
