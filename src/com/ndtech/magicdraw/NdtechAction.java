package com.ndtech.magicdraw;

import com.nomagic.magicdraw.actions.ActionsGroups;
import com.nomagic.magicdraw.actions.MDAction;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.core.project.ProjectDescriptor;
import com.nomagic.magicdraw.core.project.ProjectDescriptorsFactory;
import com.nomagic.magicdraw.core.project.ProjectsManager;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;

import javax.annotation.CheckForNull;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URI;
import java.util.List;

//import org.codehaus.jackson.map.ObjectMapper;

/**
* Action which displays its name.
*
* @author Jeff Hegedus
*/
class NdtechAction extends MDAction
{
	static String NDTECH_MAGICDRAW_PROFILE_ID = "_19_0_3_40d019c_1597099379798_500902_5145";
	static String NDTECH_MAGICDRAW_CUSTOMIZATION_PROFILE_ID = "_19_0_3_40d019c_1597100832192_484870_4796";
	
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
		GetUsedProjects("ndtech");
		JOptionPane.showMessageDialog(MDDialogParentProvider.getProvider().getDialogOwner(), "Performed action \n\nAction {\n  name: " + getName() + "\n}");
	}

//	public void ObjectToJsonString(Object o) {
//		Jsonb jsonb = JsonBuilder.create();
//	}
	

	
	private void GetUsedProjects(String moduleFilePath) {
		ProjectsManager projectsManager = Application.getInstance().getProjectsManager();
		File file = new File(moduleFilePath);
		ProjectDescriptor projectDescriptor = ProjectDescriptorsFactory.createProjectDescriptor(file.toURI());
		
//		@SuppressWarnings("deprecation")
//		Project project = projectsManager.getProject("ndtech");
		
		
		// export collection of packages as module 
		// projectsManager.exportModule(project, packages, "My local module", projectDescriptor);
		
		StringBuilder locations = new StringBuilder();
		Project prj = Application.getInstance().getProjectsManager().getActiveProject();
		List<Package> models = prj.getModels();

		
		//noinspection ConstantConditions
		List<ProjectDescriptor> projectDescriptors = ProjectDescriptorsFactory.getAvailableDescriptorsForProject(prj);
		for (int i = projectDescriptors.size() - 1; i >= 0; --i)
		{
			URI uri = projectDescriptors.get(i).getURI();
			if (uri != null)
			{
				String location = uri.toString();
				if (location != null)
				{
					locations.append(location).append("\n");
				}
			}
		}
		System.out.println("locations = " + locations);
//		JOptionPane.showMessageDialog(MDDialogParentProvider.getProvider().getDialogOwner(), locations.toString(), "Location", JOptionPane.INFORMATION_MESSAGE);
	}
	
}