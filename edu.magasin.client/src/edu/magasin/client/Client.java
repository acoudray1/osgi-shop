package edu.magasin.client;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import edu.magasin.api.ClientMagasin;
import edu.magasin.api.ListProduitObservable;
import edu.magasin.api.MagasinAchat;
import edu.magasin.api.ProductPurchaseException;
import edu.magasin.api.Produit;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.ComponentContext;

@Component(factory = Client.FACTORY)
/**
 * A Client Factory and the associated Component implementation
 * 
 * @author RL
 */
public class Client implements ClientMagasin{

	// Factory's name constant
	public static final String FACTORY = "edu.magasin.client.factory";

	// Client Component instance name
	private String name;
	
	// Products bought by the customer
	private List<Produit> boughts=new ArrayList<Produit>();
	
	// Magasin used to buy by the customer
	private MagasinAchat magasinAchat;
	
	/**
	 * Method called on instance Component initialization 
	 * 
	 * Setup the Client's name base on sent properties
	 * 
	 * @param myContext
	 */
	@Activate
	public void start(ComponentContext myContext){
		name=(String)myContext.getProperties().get("name");
	}
	
	public String getName() {
		return name;
	}
	
	@Reference(service = MagasinAchat.class, cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.DYNAMIC, unbind="unbindMagasinAchat")
	public void bindMagasinAchat(MagasinAchat aMagasin){
		magasinAchat=aMagasin;
	}
	
	public void unbindMagasinAchat(MagasinAchat aMagasin){
		magasinAchat=null;
	}

	@Override
	public ListProduitObservable getListeProduits() {
		// TODO Auto-generated method stub
		return magasinAchat.getListeProduits();
	}

	@Override
	public void addAchat(Produit aProduit, int quantity) throws ProductPurchaseException {
		magasinAchat.achatProduit(aProduit,quantity);
		Produit productCopy= new Produit();
		productCopy.setId(aProduit.getId());
		productCopy.setLabel(aProduit.getLabel());
		productCopy.setPrix(aProduit.getPrix());
		productCopy.setQuantite(quantity);
		boughts.add(aProduit);
	}

	@Override
	public double getMontantAchats() {
		Double result=0.0;
		for(Produit aProduit : boughts){
			result+=aProduit.getPrix()*aProduit.getQuantite();
		}
		return result;
	}

	
	
	
}
