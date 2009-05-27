/*
 * Created on Apr 8, 2007 by wyatt
 */
package org.homeunix.thecave.moss.swing;


public interface StandardContainer {
	/**
	 * The method to initialize the container.  This should do at minimum 
	 * the following:
	 * a) Create and add apropriate UI controls (buttons, panels, etc).  
	 *    This is generally done in the View level.  
	 * b) Create and add actions to the UI controls.  This is generally 
	 *    done at the Controller level.
	 * 
	 * In short, init() should do everything required to make a fully 
	 * function window, <b>other than</b> pack(), setVisible(true), 
	 * and any positioning methods as required for a given window.
	 * 
	 * init() <b>MUST</b> only be called once.  You should not call
	 * it directly, unless you do not call openWindow().
	 * 
	 * @return
	 */
	public void init();
	
	/**
	 * The method to update components on screen when content changes.  
	 * Essentially forces a complete redraw of the screen.  This can 
	 * potentially be a time consuming operation, so you should generally 
	 * use this method sparingly.
	 * 
	 * Do not put anything in updateContent which cannot be called 
	 * repeatedly, such as code for loading data from disk, initializing
	 * listeners, etc.  While this code is probably not to be called often,
	 * there is no guarantee that it will only be called once.  Put code
	 * which can only be called once into init(). 
	 * @return
	 */
	public void updateContent();
	
	/**
	 * The method to update buttons enabled / disabled state based on 
	 * window state.  This method is generally called from updateContent() 
	 * as part of that routine; it can also be called from action 
	 * listeners for various controls.  As this tends to be called 
	 * frequently, keep it as small as possible. 
	 * @return
	 */
	public void updateButtons();

	
	/**
	 * Override this to give your window the ability to reset to a known 
	 * clear state.
	 * @return
	 */
	public void clear();
}
