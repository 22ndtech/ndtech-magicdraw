package com.ndtech.magicdraw;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
	
	Date m_dateTime = null;
	
	public Event() {
		m_dateTime = new Date();
	}
	
	public String toString() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
		return dateFormat.format(m_dateTime);
	}
	
}
