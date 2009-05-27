/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import javax.swing.JPanel;

import org.homeunix.thecave.moss.swing.StandardContainer;

public class MossPanel extends JPanel implements StandardContainer {
	public static final long serialVersionUID = 0;
	
	/**
	 * Creates the MossPanel, and calls open() on it.
	 */
	public MossPanel() {
		this(false);
	}
	
	/**
	 * Creates the MossPanel, and calls open() on it if openManually is false.
	 * @param openManually Do not call open() automatically.  Use this if there
	 * is content within init() which requires the constructor to be finished first.
	 * Generally you will get ExceptionInInitialization errors if this is the case.
	 * 
	 * If you do decide to open the panel manually, it is highly recommended to
	 * include code to do this at the end of the constuctor (include a call
	 * to open()).  If you don't do this, you *must* do it from the calling code. 
	 */
	public MossPanel(boolean openManually) {
		if (!openManually)
			open();
	}
	
	public void clear() {}
	
	public void open() {
		init();
		updateContent();
	}
	
	public void init() {}
	
	public void updateButtons() {}
	
	public void updateContent() {
		updateButtons();
	}
}