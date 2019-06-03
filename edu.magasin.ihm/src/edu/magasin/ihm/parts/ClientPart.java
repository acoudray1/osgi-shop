/*******************************************************************************
 * Copyright (c) 2010 - 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Lars Vogel <lars.Vogel@gmail.com> - Bug 419770
 *******************************************************************************/
package edu.magasin.ihm.parts;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

import org.eclipse.core.commands.ParameterizedCommand;
import org.eclipse.e4.core.commands.ECommandService;
import org.eclipse.e4.core.commands.EHandlerService;
import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.ui.di.Focus;
import org.eclipse.e4.ui.model.application.ui.MDirtyable;
import org.eclipse.e4.ui.model.application.ui.basic.MPart;
import org.eclipse.e4.ui.workbench.modeling.EPartService;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import edu.magasin.api.ClientMagasin;
import edu.magasin.api.ListProduitObservable;
import edu.magasin.api.MagasinAchat;
import edu.magasin.ihm.labels.CountColumnLabelProvider;
import edu.magasin.ihm.labels.LabelColumnLabelProvider;
import edu.magasin.ihm.labels.PriceColumnLabelProvider;

/**
 * Part of a Client
 * 
 * @author rolucas
 */
@Component(immediate = true)
public class ClientPart implements Observer {

	// ID of the buy command
	public static final String BUY_COMMAND_ID = "edu.magasin.ihm.buyproduct";

	private Button btnBuy; // Buy button
	private TableViewer tableViewer; // Contains the Chat messages

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	@Inject
	private MDirtyable dirty; // Used to know if there's product catalogue modification

	private MPart linkedPart;

	private static ComponentFactory factory;
	private ComponentInstance myClientComponent;
	private ClientMagasin myClient;

	private static MagasinAchat magasin;

	@Inject
	ECommandService commandService; // Service used to retrieve the login
									// command
	@Inject
	EHandlerService handlerService; // Service used to launch the Command

	@Inject
	private EPartService partService;

	@Reference(target = "(component.factory=edu.magasin.client.*)", cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.DYNAMIC, unbind = "unbindFactory")
	protected void bindFactory(final ComponentFactory s, final Map<String, Object> props) {
		factory = s;
	}

	protected void unbindFactory(final ComponentFactory s, final Map<String, Object> props) {
		factory = null;
	}

	@Reference(service = MagasinAchat.class, cardinality = ReferenceCardinality.MANDATORY, policy = ReferencePolicy.DYNAMIC)
	protected void bindChatServer(final MagasinAchat aMagasin) {
		magasin = aMagasin;
	}

	protected void unbindChatServer(final MagasinAchat aMagasin) {
		magasin = null;
	}

	/**
	 * Part creation
	 * 
	 * @param parent
	 *            of the Part
	 */
	@PostConstruct
	private void createComposite(Composite parent, @Active MPart activePart) {

		// Store the current MPart object to later be able to check if it is
		// visible
		this.linkedPart = activePart;

		// Create associated new Client Component
		// 1. Get the ClientsCreationPart
		ClientsCreationPart theClientCreationPart = (ClientsCreationPart) partService
				.findPart(ClientsCreationPart.CLIENT_CREATOR_PART_ID).getObject();
		// 2. Init the component with the client name as a property
		final Hashtable<String, Object> props = new Hashtable<String, Object>();
		props.put("name", theClientCreationPart.getUserName());
		// 3. Create dedicated component instance

		myClientComponent = factory.newInstance(props);
		myClient = (ClientMagasin) myClientComponent.getInstance();
		// Create the IHM

		// 1. Set the Layout with a one column rendering
		parent.setLayout(new GridLayout(1, false));

		// 2. Create the buy button
		btnBuy = new Button(parent, SWT.BUTTON1);
		btnBuy.setText("Buy selected items");
		// Add a Listener to launch the Login action (Handler) on use
		btnBuy.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				activePart.getTransientData().put("selectedProducts", tableViewer.getSelection());
				activePart.getTransientData().put("clientComponent", myClient);

				// Retrieve the Login command
				ParameterizedCommand newClientCommand = commandService.createCommand(BUY_COMMAND_ID, null);
				// Activate the new client command (the command is linked with
				// the ClientHandler)
				handlerService.executeHandler(newClientCommand);
				// tableViewer.setSelection(null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {

			}

		});

		// 3. Create a message table viewer
		tableViewer = new TableViewer(parent);
		tableViewer.getTable().setLayoutData(new GridData(GridData.FILL_BOTH));

		// 3.1. Setup the Label column
		TableViewerColumn colLabel = new TableViewerColumn(tableViewer, SWT.NONE);
		colLabel.getColumn().setWidth(320);
		colLabel.getColumn().setText("Produit");
		colLabel.setLabelProvider(new LabelColumnLabelProvider(myClient));

		// 3.2. Setup the quantity column
		TableViewerColumn colQuantity = new TableViewerColumn(tableViewer, SWT.NONE);
		colQuantity.getColumn().setWidth(70);
		colQuantity.getColumn().setText("Quantité");
		colQuantity.setLabelProvider(new CountColumnLabelProvider(myClient));

		// 3.3. Setup the price column
		TableViewerColumn colPrice = new TableViewerColumn(tableViewer, SWT.NONE);
		colPrice.getColumn().setWidth(70);
		colPrice.getColumn().setText("Prix");
		colPrice.setLabelProvider(new PriceColumnLabelProvider(myClient));

		// 4. Setup the data for the table view
		ListProduitObservable observableProduits = (myClient.getListeProduits());
		tableViewer.setContentProvider(ArrayContentProvider.getInstance());
		tableViewer.setInput(observableProduits.getProduits());

		// 5. Add an Observer (this part) on the Observable Message Data to
		// refresh the view on modification
		observableProduits.addObserver(this);
	}

	@PreDestroy
	private void dispose(Composite parent) {
		// Free the ChatUser used component when we close the windows
		// This disconnect the user
		myClient.getListeProduits().deleteObserver(this);
		myClientComponent.dispose();
	}

	/**
	 * Used to refresh view when it get the focus
	 */
	@Focus
	public void setFocus() {
		dirty.setDirty(false);
		// txtInput.setFocus();
	}

	@Override
	public void update(Observable o, Object arg) {

		if (!partService.isPartVisible(linkedPart)) {
			dirty.setDirty(true);
		}
		tableViewer.refresh();
	}

	public void setDirty(@Active MPart activePart) {
		dirty.setDirty(true);
	}

}