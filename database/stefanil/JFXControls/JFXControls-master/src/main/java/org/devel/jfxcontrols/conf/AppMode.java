/**
 * 
 */
package org.devel.jfxcontrols.conf;

/**
 * @author stefan.illgen
 *
 */
public enum AppMode {

	/** 
	 * Application mode used for development (i.e. show errors in a verbosed way).
	 */
	DEV(false),
	/** 
	 * Application mode used for production (i.e. hide internal errors from the user).
	 * This mode is active by default (i.e. there is no one configured by the user) .
	 */
	PROD(true);
	
	// ### Standard API ###
	
	private boolean active = false;
	
	AppMode(boolean active) {
		setActive(active);
	}

	void setActive(boolean active) {
		this.active = active;
	}
	
	public boolean isActive() {
		return active;
	}
	
}
