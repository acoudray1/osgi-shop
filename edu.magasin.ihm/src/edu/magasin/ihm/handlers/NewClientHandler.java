package edu.magasin.ihm.handlers;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Execute;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.model.application.ui.basic.MPartStack;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.e4.ui.workbench.modeling.EPartService.PartState;

import edu.magasin.ihm.parts.ClientsCreationPart;

/**
 * Handler gérant la création d'un nouveau Client (Part, 
 * cette dernière se chargera de l'instanciation d'un nouveau component 
 * client dédié)
 * 
 * @author RL
 */
public class NewClientHandler {

	// ID of the part stack where the ClientPart will be placed
	public static final String ID_STACK = "edu.magasin.ihm.partstack.clients";
	// Root ID of the created Parts
	public static final String ID_PART_PREFIX = "edu.magasin.ihm.parts.client.";

	// Count of the parts created to create valid part ID
	private int partCnt = 0;

	/**
	 * Action du handler, Création d'une part (IHM) Client
	 * 
	 * @param modelService
	 *            Used to create the UserPart model element
	 * @param app
	 *            Used to to retrieve the PartStack
	 * @param partService
	 *            Used to retrieve the LoginPart
	 */
	@Execute
	public void execute(EModelService modelService, MApplication app, EPartService partService) {

		// Retrieve the ClientsCreationPart
		ClientsCreationPart theClientsCreationPart = (ClientsCreationPart) partService.findPart(ClientsCreationPart.CLIENT_CREATOR_PART_ID).getObject(); 																							// the
		// Get ClientName
		final String clientName = theClientsCreationPart.getUserName();

		// Create a new ClientPart
		partCnt++;
		MPart part = modelService.createModelElement(MPart.class);
		part.setElementId(ID_PART_PREFIX + partCnt);
		part.setContributionURI("bundleclass://edu.magasin.ihm/edu.magasin.ihm.parts.ClientPart");
		part.setCloseable(true);
		part.setLabel(clientName);
		part.getTags().add(EPartService.REMOVE_ON_HIDE_TAG);

		// Retrieve the PartStack and add the created Part in it
		MPartStack stack = (MPartStack) modelService.find(ID_STACK, app);
		if (stack != null) {
			stack.getChildren().add(part); 
			partService.showPart(part, PartState.ACTIVATE); 
		}

	}
}
