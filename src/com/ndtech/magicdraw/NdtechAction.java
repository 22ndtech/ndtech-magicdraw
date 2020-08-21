package com.ndtech.magicdraw;

import com.nomagic.magicdraw.actions.ActionsGroups;
import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;

import javax.annotation.CheckForNull;
import javax.swing.*;
import java.awt.event.ActionEvent;

//import org.codehaus.jackson.map.ObjectMapper;

/**
* Action which displays its name.
*
* @author Jeff Hegedus
*/
class NdtechAction extends MDAction
{
	public NdtechAction(@CheckForNull String id, String name)
	{
		super(id, name, null, ActionsGroups.PROJECT_OPENED_RELATED);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e)
	{
		JOptionPane.showMessageDialog(MDDialogParentProvider.getProvider().getDialogOwner(), "Performed action \n\nAction {\n  name: " + getName() + "\n}");
	}

//	public void ObjectToJsonString(Object o) {
//		Jsonb jsonb = JsonBuilder.create();
//	}
	
}