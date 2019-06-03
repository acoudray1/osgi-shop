package edu.magasin.ihm.labels;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.Display;
import edu.magasin.api.ClientMagasin;
import edu.magasin.api.Produit;

/**
 * Class used to define and customize the Message column rendering
 * 
 * @author RL
 */
public class PriceColumnLabelProvider extends ColumnLabelProvider {

	// ClientMagasin of the IHM Part
	private ClientMagasin currentClient;

	/**
	 * Constructor store the linked ChatUser to be able to customize
	 * the rendering depending his ID
	 * 
	 * @param currentUser ChatUser rendered
	 */
	public PriceColumnLabelProvider(ClientMagasin currentUser) {
		this.currentClient = currentUser;
	}

	@Override
	// Method used to define the printed text
	public String getText(Object element) {
		Produit produit = (Produit) element;
		
		return produit.getPrix()+"€";
	}

	@Override
	// Method used to define the font
	public Font getFont(Object element) {
		return new Font(Display.getCurrent(), new FontData("Arial", 12, SWT.BOLD));
	}

	@Override
	// Method used to define the color
	public Color getForeground(Object element) {
		Produit produit = (Produit) element;

		if (produit.getQuantite()<1) {
			// Product depleted
			return Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		}else if(produit.getQuantite()<5) {
			// Product soon depleted
			return Display.getDefault().getSystemColor(SWT.COLOR_RED);
		}
		// Default color
		return Display.getDefault().getSystemColor(SWT.COLOR_BLACK);
	}
}
