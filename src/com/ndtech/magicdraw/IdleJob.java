package com.ndtech.magicdraw;

import com.nomagic.magicdraw.core.Project;
import com.nomagic.magicdraw.job.Job;
import com.nomagic.magicdraw.openapi.uml.SessionManager;
import com.nomagic.task.ProgressStatus;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Class;
import com.nomagic.uml2.ext.magicdraw.classes.mdkernel.Package;
import com.nomagic.uml2.impl.ElementsFactory;

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
	private Project mProject;

	public IdleJob(Project project)
	{
		mProject = project;
	}

	@Override
	public boolean needsExecute()
	{
		return true;
	}

	@Override
	public void execute(ProgressStatus progressStatus) throws Exception
	{
		Package model = mProject.getPrimaryModel();

		SessionManager sessionManager = SessionManager.getInstance();
		sessionManager.createSession(mProject, getName());

		createClass(model);

		sessionManager.closeSession(mProject);
	}
	
	private static void isModelInitialized(Package model) {
		
	}

	private static void createClass(Package model)
	{
		ElementsFactory factory = Project.getProject(model).getElementsFactory();
		
		Class clazz = factory.createClassInstance();
		
		clazz.setOwner(model);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		clazz.setName(dateFormat.format(new Date()));
	}

	@Override
	public void finished()
	{

	}

	@Override
	public String getName()
	{
		return "Example Job";
	}

}
