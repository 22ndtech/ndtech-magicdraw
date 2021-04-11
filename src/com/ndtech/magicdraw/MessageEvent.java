package com.ndtech.magicdraw;

public class MessageEvent extends Event {
	
	private String m_message = null;

	public MessageEvent(String message) {
		m_message = message;
	}
	
	public String toString() {
		return super.toString() + "/n/t" + m_message;
	}
	
}
