package org.homeunix.thecave.moss.swing;

import java.awt.Dimension;
import java.awt.Point;

import org.homeunix.thecave.moss.swing.exception.WindowOpenException;


/**
 * An interface which defines several helper functions to a standard container (frame, dialog, etc).
 * This helps to ensure that the container is set up properly, as well as defining clear methods for
 * modifying this object at a later point in time (content change, etc).
 * 
 * If you choose to follow the MVC convention, it is recommended that classes which implement this
 * interface do so in two parts: a View portion, and a Controller portion.  One of these classes would be
 * abstract, while the other is not; when the non abstract class is instantiated, it would call apropriate
 * methods from its abstract counterpart to create a functional GUI.
 * 
 * Each of these methods returns a StandardContainer, which allows the chaining together of methods.
 * 
 * StandardContainer also implements ActionListener, to provide a common section for placing 
 * listeners in.
 * 
 * @author wolson
 */
public interface StandardWindow extends StandardContainer {
	/**
	 * The method which is run when the window is opened.  First calls
	 * init(), then updateContents(), then updateButtons().  Finally,
	 * it packs the frame and sets it visible.
	 * @return
	 * @throws WindowOpenException If there was a problem opening the window.
	 * Usually this is due to the same window being opened already (as
	 * determined by the key, in AbstractFrame derived windows.)
	 */
	public void openWindow() throws WindowOpenException;
	
	/**
	 * The method which is run when the window is opened.  First calls
	 * init(), then updateContents(), then updateButtons().  Finally,
	 * it packs the frame and sets it visible.  The window is positioned
	 * according to the dimension and positioning values given.
	 * @return
	 * @throws WindowOpenException If there was a problem opening the window.
	 * Usually this is due to the same window being opened already (as
	 * determined by the key, in AbstractFrame derived windows.)
	 */
	public void openWindow(Dimension dimension, Point position) throws WindowOpenException;	
	
	/**
	 * The method used to close the window.  Can return an object
	 * if desired; if you don't need to return anything to the calling
	 * method, just return null.
	 * @return
	 */
	public Object closeWindow();

	/**
	 * The method used to close the window without checking canClose().
	 * @return
	 */
	public void closeWindowWithoutPrompting();
	
	/**
	 * Checks if you can close the window.  The default implementation MUST return true always;
	 * you can override this to check conditions, and perhaps prompt the user for a save
	 * or something before returning.  If this method returns false, the close operation
	 * is cancelled.
	 *  
	 * @return Returns true if you can close the window, false otherwise. 
	 */
	public boolean canClose();
	
	
	/**
	 * A method which is called after the pack() method in openWindow,
	 * but before the setVisible(true).  This allows you to do some 
	 * initialization based on the post-packed size of components.
	 * @return
	 */
	public void initPostPack();
	
	/**
	 * Request that this frame gets focus.  This is more strong than the
	 * requestFocusInWindow() method, and should succeed more often.
	 * The default implementation is to call setVisible(true), followed 
	 * by requestFocusInWindow().
	 */
	public void requestFocusInApplication();
}
