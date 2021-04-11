package com.ndtech.magicdraw;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.job.Job;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.task.ProgressStatus;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.impl.ElementsFactory;
import com.nomagic.magicdraw.uml.Finder;
import com.nomagic.uml2.ext.jmi.helpers.StereotypesHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ndtech idle job
 * 
 * @author Jeff Hegedus
 *
 */
public class IdleJob implements Job{
	private NdtechProject m_ndtechProject;
	private Project m_magicdrawProject = null;
	private SessionManager m_sessionManager = null;

	public IdleJob(NdtechProject ndtechProject)
	{
		m_ndtechProject = ndtechProject;
		m_magicdrawProject = ndtechProject.GetMagicdrawProject();
	}

	@Override
	public boolean needsExecute()
	{
		return true;
	}

	@Override
	public void execute(ProgressStatus progressStatus) throws Exception
	{
		Package model = m_ndtechProject.getPrimaryModelPackage();

//		SessionManager sessionManager = SessionManager.getInstance();
//		sessionManager.createSession(m_magicdrawProject, getName());

//		createClass(model);
//		logMessage("IdleJob.execute(progressStatus)");
//		experiment();

//		sessionManager.closeSession(m_magicdrawProject);
	}
	
	private void experiment() {
		logMessage("IdleJob.experiment() -- Begin");
	
//		StereotypesHelper.
	}
	
	private void StartSession() {
		m_sessionManager = SessionManager.getInstance();
		m_sessionManager.createSession(m_magicdrawProject, getName());
	}
	
	private void EndSession() {
		m_sessionManager.closeSession(m_magicdrawProject);
	}

	@Override
	public void finished()
	{

	}

	@Override
	public String getName()
	{
		return "com.ndtech.magicdraw.IdleJob";
	}
	
	private static void createClass(Package model)
	{
		ElementsFactory factory = Project.getProject(model).getElementsFactory();
		
		Class clazz = factory.createClassInstance();
		
		clazz.setOwner(model);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		clazz.setName(dateFormat.format(new Date()));
	}
	
	private static void logMessage(String message) {
		logEvent(new MessageEvent(message));
	}
	
	private static void logMessage(String message, Object context) {
		logEvent(new MessageEvent(message), context);
	}
	
	private static void logEvent(Event event) {
		Application.getInstance().getGUILog().log("com.ndtech.magicdraw" + "\n" + event.toString());	
	}
	
	private static void logEvent(Event event, Object context) {
		Application.getInstance().getGUILog().log("com.ndtech.magicdraw." + context.getClass().getTypeName() + "\n" + event.toString());	
	}

}
