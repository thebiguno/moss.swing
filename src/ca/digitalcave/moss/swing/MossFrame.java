/*
 * Created on May 6, 2006 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import org.homeunix.thecave.moss.common.OperatingSystemUtil;

import ca.digitalcave.moss.swing.exception.WindowOpenException;

/**
 * AbstractFrame is core of the Moss framework for creating easy to use 
 * and powerful windows.  If you want extra functions for Document based
 * windows, try using AbstractDocumentFrame.
 *  
 * @author wyatt
 *
 */
public class MossFrame extends JFrame implements StandardWindow, StandardContainer {
	public static final long serialVersionUID = 0;

	private boolean alreadyInit = false;
	private final Object key; //Key into openFrames to determine if the frame is already open
	private final ApplicationModel application;
	private boolean documentBasedApplication = true; //Determines some OSX behaviour, such as exit on last window close.

	/**
	 * Creates a new MossFrame.
	 */
	public MossFrame() {
		this(null);
	}

	/**
	 * Creates a new MossFrame.
	 * @param key Key associated with the frame.  You are guaranteed to only 
	 * have at most one frame with the same key.  If you try to open a new
	 * frame with the same key, it will cancel the creation and set focus
	 * to the existing frame.  If key is null, it is ignored.
	 */
	public MossFrame(Object key) {
		this.application = ApplicationModel.getInstance();
		this.key = key;
	}

	public void openWindow() throws WindowOpenException {
		openWindow(true, null, null, false);
	}
	public void openWindow(Dimension dimension, Point position) throws WindowOpenException {
		openWindow(true, dimension, position, false);
	}
	public void openWindow(boolean closeExisting) throws WindowOpenException {
		openWindow(true, null, null, closeExisting);
	}
	public void openWindow(Dimension dimension, Point position, boolean closeExisting) throws WindowOpenException {
		openWindow(true, dimension, position, closeExisting);
	}	
	private void openWindow(boolean visible, Dimension dimension, Point position, boolean closeExisting) throws WindowOpenException {
		MossFrame frameToClose = null;
		if (application.isFrameOpen(key)){
			if (closeExisting){
				frameToClose = application.getFrame(key);
			}
			else {
				application.getFrame(key).requestFocusInApplication();
				throw new WindowOpenException("Frame " + key + " already open.  Cancelling open request, and calling it to the foreground.");
			}
		}

		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if (OperatingSystemUtil.isMacAqua()) {
			this.getRootPane().setBorder(BorderFactory.createCompoundBorder(
					BorderFactory.createEmptyBorder(12, 15, 15, 15),
					this.getRootPane().getBorder()));			
		}

		if (!alreadyInit){
			this.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent arg0) {
					MossFrame.this.closeWindow();
				}
			});

			init();
		}

		if (dimension != null)
			this.setPreferredSize(dimension);
		this.pack();

		updateContent();
		updateButtons();

		if (!alreadyInit){
			initPostPack();
		}

		if (position != null)
			this.setLocation(position);
		else
			this.setLocationRelativeTo(null);
		
		this.setVisible(visible);
		
		if (key != null)
			application.addOpenFrame(key, this);

		alreadyInit = true;
		
		//We need to delay closing the old frame until the new one has been opened,
		// or the program may exit (if all other non-daemon threads are complete).
		if (frameToClose != null){
			frameToClose.closeWindowWithoutPrompting(false);
		}		
	}

	/**
	 * Checks if you can close the window.  The default implementation returns true always;
	 * you can override this to check conditions, and perhaps prompt the user for a save
	 * or something before returning.  If this method returns false, the close operation
	 * is cancelled.
	 *  
	 * @return Returns true if you can close the window, false otherwise. 
	 */
	public boolean canClose(){
		return true;
	}

	public Object closeWindow() {
		if (canClose()){
			closeWindowWithoutPrompting();
		}

		return null;
	}

	/**
	 * Forces the window to close, without giving the user the option to save.
	 */
	public void closeWindowWithoutPrompting(){
		closeWindowWithoutPrompting(true);
	}
	
	private void closeWindowWithoutPrompting(boolean removeKey){
		this.setVisible(false);
		this.dispose();

		if (key != null && removeKey)
			application.removeOpenFrame(key);
		
		//On non-Mac systems, we check how many windows are left open.  If this was the last one,
		// we close the program.
		// On Macintosh, it is the correct behaviour to leave applications running even without
		// any active windows, so we skip it for this OS.
		if ((!OperatingSystemUtil.isMac()
				|| !isDocumentBasedApplication())
				&& application.getOpenFrames().size() == 0){
			Logger.getLogger(MossFrame.class.getName()).finest("Final window has closed; exiting application");
			System.exit(0);
		}
	}

	/* (non-Javadoc)
	 * @see org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer#clear()
	 */
	public void clear() {}

	public void initPostPack() {}

	public void updateButtons() {
		updateMenus();
	}

	public void updateContent() {
		updateButtons();
	}

	public void init() {
		this.addWindowFocusListener(new WindowFocusListener(){
			public void windowGainedFocus(WindowEvent e) {
				updateContent();
				updateMenus();
			}
			public void windowLostFocus(WindowEvent e) {
				updateContent();
				updateMenus();
			}
		});
	}
	
	
	@Override
	public void setJMenuBar(JMenuBar menubar) {
		super.setJMenuBar(menubar);
		
		updateMenus();
	}

	/**
	 * Updates the AbstractMenuBar and all sub AbstractMenu* objects associated with this frame.
	 */
	public void updateMenus(){
		if (getMossMenuBar() != null)
			getMossMenuBar().updateMenus();
	}

	private MossMenuBar getMossMenuBar(){
		if (getJMenuBar() instanceof MossMenuBar)
			return (MossMenuBar) getJMenuBar();
		return null;
	}

	public void requestFocusInApplication() {
		this.setVisible(true);
		this.requestFocusInWindow();		
	}
	
	public boolean isDocumentBasedApplication() {
		return documentBasedApplication;
	}
	public void setDocumentBasedApplication(boolean documentBasedApplication) {
		this.documentBasedApplication = documentBasedApplication;
	}
}