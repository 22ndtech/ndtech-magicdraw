package com.ndtech.magicdraw;

import com.nomagic.actions.AMConfigurator;
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.ActionsManager;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.MDActionsCategory;

/**
* Class for configuring main menu and adding  new sub-menu.
* @author Jeff Hegedus
*/
public class MainMenuConfigurator implements AMConfigurator
{

	String EXAMPLES="Examples";

	/**
	 * Action will be added to manager.
	 */
	private NMAction action;
	private String parentMenuName = new String();

	/**
	 * Creates configurator. Used when menu should be top level
	 * @param action action to be added to main menu.
	 */
	public MainMenuConfigurator(NMAction action)
	{
		this.action = action;
	}

	/**
	 * Creates configurator. Used when menu should be under an existing top level menu
	 * @param action action to be added to main menu.
	 * @param parentMenuName name of the menu under which this menu should be added
	 */
	public MainMenuConfigurator(NMAction action, String parentMenuName)
	{
		this.action = action;
		this.parentMenuName = parentMenuName;
	}

	/**
	 * @see com.nomagic.actions.AMConfigurator#configure(ActionsManager)
	 *  Methods adds action to given manager Examples category.
	 */
	@Override
	public void configure(ActionsManager manager)
	{
		ActionsCategory category = null;
		
		if(parentMenuName.isEmpty()) {
			// creating new category with the name of the action
			category = new MDActionsCategory(null, action.getName());
			category.setNested(false);
			manager.addCategory(category);
		}
		else {
			// searching for the parentMenuName
			category = (ActionsCategory) manager.getActionFor(parentMenuName);
			if(category == null) {
				category = new MDActionsCategory(null, parentMenuName);
				category.setNested(true);
				manager.addCategory(category);
			}
		}

		if(action != null) {
			category.addAction(action);
		}
	}
	
	@Override
	public int getPriority()
	{
		return AMConfigurator.MEDIUM_PRIORITY;
	}

}