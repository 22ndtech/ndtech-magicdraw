package com.ndtech.magicdraw;

// base magicdraw imports
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.plugins.Plugin;
import com.nomagic.magicdraw.plugins.ResourceDependentPlugin;
import com.nomagic.magicdraw.ui.dialogs.MDDialogParentProvider;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Element;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Property;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.impl.ElementsFactory;
// project imports
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.core.ProjectUtilities;
import com.nomagic.magicdraw.core.project.ProjectDescriptor;
import com.nomagic.magicdraw.core.project.ProjectDescriptorsFactory;
import com.nomagic.magicdraw.core.project.ProjectEventListenerAdapter;
import com.nomagic.magicdraw.core.project.ProjectsManager;
// idle job imports
import com.nomagic.magicdraw.job.Job;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.job.IdleJobService;

// actions imports
import com.nomagic.actions.ActionsCategory;
import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;

import java.io.File;
import java.net.URI;
import java.util.Collection;
// base java imports
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

public class MainPlugin extends Plugin implements ResourceDependentPlugin {
	static String NDTECH_MAGICDRAW_PROFILE_ID = "_19_0_3_40d019c_1597099379798_500902_5145";
	static String NDTECH_MAGICDRAW_CUSTOMIZATION_PROFILE_ID = "_19_0_3_40d019c_1597100832192_484870_4796";
	static String NDTECH_MAGICDRAW_PLUGIN_VERSION = "0.0.1";
	static String NDTECH_MAGIC_DRAW_PLUGIN_NAME = "ndtech-magicdraw-plugin";
	static String NDTECH_MAGIC_DRAW_CUSTOMIZATION_PROFILE_NAME = "ndtech-magicdraw-profile-customizations";
	static String NDTECH_MAGICDRAW_PROJECT_CONFIGURATION_NAME = "ndtech-magicdraw-project-configuration";

	boolean m_isNdtechProject = false;

	Profile m_ndtechMagicDrawProfile = null;
	Profile m_ndtechMagicDrawCustomizationProfile = null;
	com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package m_primaryModelPackage = null;
	com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package m_ndtechConfigurationPackage = null;

	public static boolean m_initialized;
	// We track jobs by project.
	private Map<Project, Job> m_projectJobs = new HashMap<>();
	NMAction m_addJobAction = null;
	MainMenuConfigurator m_jobsMenuConfigurator = null;
	ActionsConfiguratorsManager m_actionsConfigurationManager = ActionsConfiguratorsManager.getInstance();

	com.ndtech.magicdraw.NdtechProject m_ndtechProject = null;

	private Map<Project, NdtechProject> m_ndtechProjects = new HashMap<>();

	@Override
	public void init() {
//		Application.getInstance().getGUILog().showMessage("com.ndtech.magicdraw.MainPlugin initialized.");
		Application.getInstance().getGUILog().log("com.ndtech.magicdraw.MainPlugin initialized.");

//		m_addJobAction = getAddJobAction();
//		m_addJobAction.setEnabled(false);
//		m_jobsMenuConfigurator = new MainMenuConfigurator(m_addJobAction, new String());
//		
//		ActionsConfiguratorsManager acm = ActionsConfiguratorsManager.getInstance();
//		acm.addMainMenuConfigurator(m_jobsMenuConfigurator);

		addProjectListener();
		m_initialized = true;
	}

	private void addProjectListener() {
		Application.getInstance().addProjectEventListener(new ProjectEventListenerAdapter() {
			@Override
			public void projectOpened(Project magicdrawProject) {
				// Enable the menu item to add a new job
//				m_addJobAction.setEnabled(true);
				m_ndtechProject = new NdtechProject(Application.getInstance().getProjectsManager().getActiveProject());
				m_ndtechProjects.put(magicdrawProject, m_ndtechProject);
				m_ndtechProject.run();
			}

			@Override
			public void projectDeActivated(Project magicdrawProject) {
				NdtechProject ndtechProject = m_ndtechProjects.get(magicdrawProject);
				ndtechProject.stop();
			}

			@Override
			public void projectActivated(Project magicdrawProject) {
				NdtechProject ndtechProject = m_ndtechProjects.get(magicdrawProject);
				ndtechProject.run();
			}

			@Override
			public void projectPreClosed(Project magicdrawProject) {
				NdtechProject ndtechProject = m_ndtechProjects.get(magicdrawProject);
				ndtechProject.stop();
			}
		});
	}

	@SuppressWarnings("unused")
	private void initializeMenuItemsExample() {
		ActionsConfiguratorsManager acm = ActionsConfiguratorsManager.getInstance();
		acm.addMainMenuConfigurator(new MainMenuConfigurator(getSubMenuActions(), "test"));
		acm.addMainMenuConfigurator(new MainMenuConfigurator(getSeparatedActions(), "test"));
		acm.addMainMenuConfigurator(new MainMenuConfigurator(getStateAction(), "test"));
		acm.addMainMenuConfigurator(new MainMenuConfigurator(getGroupedStateAction(), "test"));
	}

	private static NMAction getAddJobAction() {
		ActionsCategory category = new ActionsCategory(null, "Jobs");
		category.setNested(true);
		category.addAction(new NdtechAction(null, "Create Job"));
		return category;
	}

	private static NMAction getSubMenuActions() {
		ActionsCategory category = new ActionsCategory(null, "SubMenu");
		category.setNested(true);
		category.addAction(new SimpleAction(null, "SubAction1"));
		category.addAction(new SimpleAction(null, "SubAction2"));
		return category;
	}

	/**
	 * Creates group of actions. This group is separated from others using menu
	 * separator (when it represented in menu). Separator is added for group of
	 * actions in one actions category.
	 */
	private static NMAction getSeparatedActions() {
		ActionsCategory category = new ActionsCategory();
		category.addAction(new SimpleAction(null, "Action1"));
		category.addAction(new SimpleAction(null, "Action2"));
		return category;
	}

	/**
	 * Creates action which is represented by JCheckBoxMenuItem.
	 */
	private static NMAction getStateAction() {
		return new SimpleStateAction("StateAction", "State Action");
	}

	/**
	 * @return action which represents state action groups. It is represented in
	 *         menu by JRadioButtonMenuItem.
	 */
	private static NMAction getGroupedStateAction() {
		String choice[] = new String[1];

		SimpleChoiceAction a1 = new SimpleChoiceAction("R1", "R1", choice);
		SimpleChoiceAction a2 = new SimpleChoiceAction("R2", "R2", choice);
		choice[0] = a1.getName();
		a1.updateState();
		a2.updateState();
		// actions must be added to one category.
		ActionsCategory cat = new ActionsCategory();
		cat.addAction(a1);
		cat.addAction(a2);
		return cat;
	}

	@Override
	public boolean close() {
		return true;
	}

	@Override
	public boolean isSupported() {
		return true;
	}

	@Override
	public String getPluginName() {
		// TODO Auto-generated method stub
		return NDTECH_MAGIC_DRAW_PLUGIN_NAME;
	}

	@Override
	public String getPluginVersion() {
		// TODO Auto-generated method stub
		return NDTECH_MAGICDRAW_PLUGIN_VERSION;
	}

	@Override
	public boolean isPluginRequired(Project project) {
		return ProjectUtilities.findAttachedProjectByName(project,
				NDTECH_MAGIC_DRAW_CUSTOMIZATION_PROFILE_NAME) != null;
	}
}
