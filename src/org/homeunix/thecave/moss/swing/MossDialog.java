/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Window;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;

import org.homeunix.thecave.moss.common.OperatingSystemUtil;
import org.homeunix.thecave.moss.swing.exception.WindowOpenException;

public class MossDialog extends JDialog implements StandardContainer, StandardWindow {
	public static final long serialVersionUID = 0;
		
	private Window owner;
	private boolean alreadyInit = false;
	private boolean modal;
	
	/**
	 * Creates a new AbstractDialog associated with the given frame.  This frame is used for a number
	 * of things, including modality and screen position.
	 * @param owner
	 */
	public MossDialog(MossDialog owner, boolean modal){
		super(owner);
		this.setResizable(true);
		this.owner = owner;
		this.modal = modal;
	}
	
	public MossDialog(MossDialog owner){
		this(owner, true);
	}

	/**
	 * Creates a new AbstractDialog associated with the given frame.  This frame is used for a number
	 * of things, including modality and screen position.
	 * @param owner
	 */
	public MossDialog(MossFrame owner, boolean modal){
		super(owner);
		this.setResizable(true);
		this.owner = owner;
		this.modal = modal;
	}
	
	public MossDialog(MossFrame owner){
		this(owner, true);
	}

	
	public void openWindow() throws WindowOpenException {
		openWindow(true, null, null);
	}
	public void openWindow(Dimension dimension, Point position) throws WindowOpenException {
		openWindow(true, dimension, position);	
	}	
	private void openWindow(boolean visible, Dimension dimension, Point position) throws WindowOpenException {		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		if (OperatingSystemUtil.isMac()) {
			this.getRootPane().setBorder(BorderFactory.createEmptyBorder(7, 12, 12, 12));			
		}
		
		if (!alreadyInit){
			this.addWindowListener(new WindowAdapter(){
				@Override
				public void windowClosing(WindowEvent arg0) {
					MossDialog.this.closeWindow();
				}
			});

			init();
		}
				
		this.setModal(modal);
		this.setPreferredSize(dimension);
		this.pack();
		
		updateContent();
		updateButtons();
		
		if (!alreadyInit){
			initPostPack();
		}

		if (position != null){
			this.setLocation(position);	
		}
		else {
			this.setLocationByPlatform(true);
			this.setLocationRelativeTo(owner);
		}

		this.setVisible(visible);
		alreadyInit = true;
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
	
	/* (non-Javadoc)
	 * @see org.homeunix.thecave.moss.gui.abstractwindows.StandardContainer#closeWindow()
	 */
	public Object closeWindow() {
		if (canClose()){
			this.setVisible(false);
			this.dispose();
		}
		
		return null;
	}
	
	public void closeWindowWithoutPrompting(){
		this.setVisible(false);
		this.dispose();
	}

	
	public void clear() {}
	
	public void initPostPack() {}
	
	public void init() {}
	
	public void updateButtons() {}
	
	public void updateContent() {}
	
	public void requestFocusInApplication() {
		this.setVisible(true);
		this.requestFocusInWindow();		
	}
}