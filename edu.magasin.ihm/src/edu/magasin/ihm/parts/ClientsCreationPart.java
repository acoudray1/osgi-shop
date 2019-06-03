 
package edu.magasin.ihm.parts;

import javax.inject.Inject;
import javax.annotation.PostConstruct;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;


import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.ui.workbench.UIEvents.Command;
/**
 * Part used to define User
 * @author rolucas
 */
public class ClientsCreationPart{
	
	// ID of the part
	public static final String CLIENT_CREATOR_PART_ID="edu.magasin.ihm.parts.clientcreation";
	// ID of the login command
	public static final String CLIENT_CREATOR_COMMAND_ID="edu.magasin.ihm.newclient";
	
	private Text txtClientName; // ClientName Input
	private Button btnConnect; // Connection button
	
	@Inject ECommandService commandService; // Service used to retrieve the login command
	@Inject EHandlerService handlerService; // Service used to launch the Command
		
	
	/**
	 * Part constructor
	 * @param parent parent Composite
	 */
	@PostConstruct
	public void postConstruct(Composite parent) {
		
		// Define the rendering Layout with 2 row
		parent.setLayout(new GridLayout(2, false));
		
		// create ClientName input field
		txtClientName = new Text(parent, SWT.BORDER);
		txtClientName.setText("Nom du client...");
		txtClientName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		txtClientName.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// No action
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				
				// Retrieve the Login command
				ParameterizedCommand newClientCommand=commandService.createCommand(CLIENT_CREATOR_COMMAND_ID, null);
				// Activate the new client command (the commande is linked with the ClientHandler)
				handlerService.executeHandler(newClientCommand);
				txtClientName.setText("");
			}
			
		});
		// create the Login button
		btnConnect = new Button(parent, SWT.BUTTON1);
		btnConnect.setText("Créer client");
		// Add a Listener to launch the Login action (Handler) on use
		btnConnect.addSelectionListener(new SelectionListener(){

			@Override
			public void widgetSelected(SelectionEvent e) {
				// Retrieve the Login command
				ParameterizedCommand newClientCommand=commandService.createCommand(CLIENT_CREATOR_COMMAND_ID, null);
				// Activate the Login command (the commande is linked with the LoginHandler)
				handlerService.executeHandler(newClientCommand);
				txtClientName.setText("");
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				// No action
			}
			
		});
	}
	
	/**
	 * UserName getter
	 * @return the typed user name
	 */
	public String getUserName(){
		return txtClientName.getText();
	}
}