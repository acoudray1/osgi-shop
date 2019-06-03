package edu.magasin.fournisseur;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import edu.magasin.api.FournisseurMagasin;
import edu.magasin.api.MagasinApprovisionnement;
import edu.magasin.api.ProductCreationException;
import edu.magasin.api.Produit;

@Component
/**
 * Service Component permettant d'ajouter des produits au Magasin
 * @author rolucas
 *
 */
public class Fournisseur implements FournisseurMagasin{

	static int idProduct=0;
	public MagasinApprovisionnement approvisionnement;
	/**
	 * Récupération du Magasin via son interface d'approvisionnement
	 * @param aMagasinApprovisionnement
	 */
	@Reference(service = MagasinApprovisionnement.class, cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.DYNAMIC)
	protected void bindMagasinApprovisonnement(final MagasinApprovisionnement aMagasinApprovisionnement) {
		approvisionnement = aMagasinApprovisionnement;
	}

	protected void unbindMagasinApprovisonnement(final MagasinApprovisionnement aMagasinApprovisionnement) {
		approvisionnement = null;
	}
	
	@Override
	public void addProduct(String name, String price, String count) throws ProductCreationException {
		try{
			int nbProduct=Integer.parseInt(count);
			double prix=Double.parseDouble(price);
			Produit produit= new Produit();
			produit.setId(idProduct++);
			produit.setLabel(name);
			produit.setPrix(prix);
			produit.setQuantite(nbProduct);
			approvisionnement.addProduct(produit);
			
		}catch(Exception e){
			throw new ProductCreationException("Erreur de création du produit : "+e.getStackTrace());
		}
		
	}

}
