package com.ndtech.magicdraw;

import java.util.HashMap;
import java.util.Map;

import com.nomagic.actions.NMAction;
import com.nomagic.magicdraw.actions.ActionsConfiguratorsManager;
import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.job.IdleJobService;
import com.nomagic.magicdraw.job.Job;
import com.nomagic.magicdraw.openapi.uml.ModelElementsManager;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.ext.magicdraw.mdprofiles.Profile;
import com.nomagic.uml2.impl.ElementsFactory;

public class NdtechProject {
	static String NDTECH_MAGICDRAW_PROFILE_ID = "_19_0_3_40d019c_1597099379798_500902_5145";
	static String NDTECH_MAGICDRAW_CUSTOMIZATION_PROFILE_ID = "_19_0_3_40d019c_1597100832192_484870_4796";
	static String NDTECH_MAGICDRAW_PLUGIN_VERSION = "0.0.1";
	static String NDTECH_MAGIC_DRAW_PLUGIN_NAME = "ndtech-magicdraw-plugin";
	static String NDTECH_MAGIC_DRAW_CUSTOMIZATION_PROFILE_NAME = "ndtech-magicdraw-profile-customizations";
	static String NDTECH_MAGICDRAW_PROJECT_CONFIGURATION_NAME = "ndtech-magicdraw-project-configuration";
	
	com.nomagic.magicdraw.core.Project m_magicdrawProject;
	
	boolean m_isNdtechProject = false;
	Profile m_ndtechMagicDrawProfile = null;
	Profile m_ndtechMagicDrawCustomizationProfile = null;
	com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package m_primaryModelPackage = null;
	com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package m_ndtechConfigurationPackage = null;
	
	Job m_job;
	
	public static boolean m_initialized;
	// We track jobs by project.
	NMAction m_addJobAction = null;
	MainMenuConfigurator m_jobsMenuConfigurator = null;
	ActionsConfiguratorsManager m_actionsConfigurationManager = ActionsConfiguratorsManager.getInstance();
	
	private boolean m_running = false;
	
	
	public NdtechProject(com.nomagic.magicdraw.core.Project project) {
		m_magicdrawProject = project;
		init();
	}
	
	private void init() {
		m_ndtechMagicDrawCustomizationProfile = (Profile) m_magicdrawProject.getElementByID(NDTECH_MAGICDRAW_CUSTOMIZATION_PROFILE_ID);
		if(m_ndtechMagicDrawCustomizationProfile != null) {
			
			m_ndtechMagicDrawProfile = (Profile) m_magicdrawProject.getElementByID(NDTECH_MAGICDRAW_PROFILE_ID);
			
			m_isNdtechProject = true;
			
			m_primaryModelPackage = (Package)m_magicdrawProject.getPrimaryModel();
			m_ndtechConfigurationPackage = Finder.byQualifiedName().find(m_magicdrawProject, NDTECH_MAGICDRAW_PROJECT_CONFIGURATION_NAME);
			
			if(m_ndtechConfigurationPackage == null) {
				SessionManager.getInstance().createSession(m_magicdrawProject, NDTECH_MAGICDRAW_PROJECT_CONFIGURATION_NAME);
				
				try {
					ElementsFactory elementsFactory = m_magicdrawProject.getElementsFactory();
					m_ndtechConfigurationPackage = elementsFactory.createPackageInstance();
					m_ndtechConfigurationPackage.setName(NDTECH_MAGICDRAW_PROJECT_CONFIGURATION_NAME);
					m_ndtechConfigurationPackage.setOwner(m_primaryModelPackage);
					ModelElementsManager.getInstance().addElement(m_primaryModelPackage, m_ndtechConfigurationPackage);;
				}
				catch(Exception e) {
					Application.getInstance().getGUILog().log("com.ndtech.magicdraw.MainPlugin initialized.");
				}
				
				SessionManager.getInstance().closeSession(m_magicdrawProject);
			}
			
			configure();
			
			run();
		}
	}
	
	public com.nomagic.magicdraw.core.Project GetMagicdrawProject(){
		return m_magicdrawProject;
	}
	
	
	public com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package getPrimaryModelPackage(){
		return m_primaryModelPackage;
	}
	
	public boolean isNdtechPackage() {
		return m_isNdtechProject;
	}
	
	
	public void configure() {
		
	}
	
	public void run() {
		m_running = true;
		
		// Add an idle job for the project
		// The idle job runs repeatedly and performs all work for the plugin
		m_job = new IdleJob(this);
		IdleJobService.getInstance().addJob(m_job);
	}
	
	public void stop() {
		m_running = false;
		
		IdleJobService.getInstance().removeJob(m_job);
	}
	
	
}
