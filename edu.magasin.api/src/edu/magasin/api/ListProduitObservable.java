package edu.magasin.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * Les (Observable) des produits du Magasin
 * 
 * La liste est observable pour permettre par exemple à une IHM d'être informée des mises à jour
 * 
 * @author RL
 *
 */
public class ListProduitObservable extends Observable{
	
	private List<Produit> products=new ArrayList<Produit>();

	public List<Produit> getProduits() {
		return products;
	}
	
	public void addProduit(Produit aProduit){
		products.add(aProduit);
		change();
	}
	
	public void change(){
		this.setChanged();
		this.notifyObservers();
	}
}

