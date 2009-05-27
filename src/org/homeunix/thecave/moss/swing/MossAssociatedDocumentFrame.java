/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * AbstractAssociatedFrame is for a frame which is a logical association of another.
 * For instance, if you have a group of windows associated with a single document, 
 * if you close the main window you want all the associated windows to close as well.
 * This class automates that functionality.
 * 
 * Since it must be associated with another AbstractDocumentFrame, the document associated 
 * with this frame is obtained from the parent frame.
 * 
 * @author wyatt
 *
 */
public class MossAssociatedDocumentFrame extends MossDocumentFrame {
	public static final long serialVersionUID = 0;
	
	/**
	 * Creates a new AbstractFrame with the specified key.  If key is null,
	 * it is ignored.
	 * @param key Key associated with the frame.  You are guaranteed to only 
	 * have at most one frame with the same key.  If you try to open a new
	 * frame with the same key, it will cancel the creation and set focus
	 * to the existing frame.
	 */
	public MossAssociatedDocumentFrame(MossDocumentFrame parent, Object key) {
		super(parent.getDocument(), key);
		
		
		//If the parent closes, we want to close too.
		parent.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosed(WindowEvent e) {
				MossAssociatedDocumentFrame.this.closeWindowWithoutPrompting();
			}
		});
	}
}