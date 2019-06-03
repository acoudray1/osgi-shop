package edu.magasin.ihm.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import edu.magasin.api.FournisseurMagasin;
import edu.magasin.api.ProductCreationException;
import edu.magasin.ihm.parts.ClientsCreationPart;
import edu.magasin.ihm.parts.FournisseurPart;

/**
 * PRemet de gérer l'action d'ajout d'un produit.
 * L'intérêt d'utiliser un Handler est qu'il est possible de l'associer à divers actions :
 *  - Bouton dans une page
 *  - Action une barre des tâches
 *  -...
 *  
 *  Un handler est réutilisable...
 * 
 * @author RL
 */
@Component
public class AddProductHandler {

	private static FournisseurMagasin fournisseur;
	
	@Reference(service = FournisseurMagasin.class, cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.DYNAMIC)
	protected void bindFournisseur(final FournisseurMagasin aFournisseurMagasin) {
		fournisseur = aFournisseurMagasin;
	}

	protected void unbindFournisseur(final FournisseurMagasin aFournisseurMagasin) {
		fournisseur = null;
	}
	
	/**
	 * Action réalisée par le handler, permet l'ajout d'un produit au Magasin en utilisant le Fournisseur
	 * 
	 * @param modelService
	 *            Used to create the UserPart model element
	 * @param app
	 *            Used to to retrieve the PartStack
	 * @param partService
	 *            Used to retrieve the LoginPart
	 */
	@Execute
	public void execute(final Shell shell, EModelService modelService, MApplication app, EPartService partService) {

		// Retrieve the ClientsCreationPart
		FournisseurPart theFournisseurPart = (FournisseurPart) partService.findPart(FournisseurPart.FOURNISSEUR_PART_ID)
				.getObject(); // the
	

		try {
			fournisseur.addProduct(theFournisseurPart.getProductName(), theFournisseurPart.getProductPrice(),theFournisseurPart.getProductCount());
		} catch (ProductCreationException e) {
			// create a dialog 
			MessageBox dialog =
			    new MessageBox(shell, SWT.ICON_ERROR | SWT.OK);
			dialog.setText(e.getClass().toString());
			dialog.setMessage(e.getMessage());

			// open dialog
			dialog.open();
		}

	}
}
