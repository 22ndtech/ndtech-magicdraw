package com.ndtech.magicdraw;

import com.nomagic.magicdraw.core.Application;
import com.nomagic.magicdraw.plugins.Plugin;

public class MainPlugin extends Plugin
{
	public static boolean initialized;
	
	@Override
	public void init()
	{
		initialized = true;
		Application.getInstance().getGUILog().showMessage("com.ndtech.magicdraw.MainPlugin initialized.");
	}

	@Override
	public boolean close()
	{
		return true;
	}

	@Override
	public boolean isSupported()
	{
		return true;
	}
}
