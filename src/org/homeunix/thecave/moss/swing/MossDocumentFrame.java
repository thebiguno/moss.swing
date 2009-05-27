/*
 * Created on May 6, 2006 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import org.homeunix.thecave.moss.application.document.StandardDocument;
import org.homeunix.thecave.moss.common.OperatingSystemUtil;


/**
 * AbstractDocumentFrame is the class which contains document specific logic.
 * 
 * @author wyatt
 *
 */
public class MossDocumentFrame extends MossFrame {
	public static final long serialVersionUID = 0;
	
	private final StandardDocument document;
	
	/**
	 * Creates a new AbstractDocumentFrame with the specified key.  If key is null,
	 * it is ignored.
	 * @param key Key associated with the frame.  You are guaranteed to only 
	 * have at most one frame with the same key.  If you try to open a new
	 * frame with the same key, it will cancel the creation and set focus
	 * to the existing frame.
	 */
	public MossDocumentFrame(StandardDocument document, Object key) {
		super(key);
		
		this.document = document;
	}
	
	@Override
	public void updateContent() {
		super.updateContent();
		
		final String UNSAVED_MARK = "* ";
		final String baseTitle = (this.getTitle().startsWith(UNSAVED_MARK) ? this.getTitle().substring(UNSAVED_MARK.length(), this.getTitle().length()) : this.getTitle());
		
		//Update the title bar.  On non Mac systems, we also put a * at the beginning if 
		// the data file is not saved.
		if (isDocumentSaved()){
			if (!OperatingSystemUtil.isMac()) { 
				this.setTitle(baseTitle);
			}
			this.getRootPane().putClientProperty("windowModified", Boolean.FALSE);				
		}
		else {
			if (!OperatingSystemUtil.isMac())
				this.setTitle(UNSAVED_MARK + baseTitle);
			this.getRootPane().putClientProperty("windowModified", Boolean.TRUE);
		}
	}
	
	/**
	 * Determines if the document is saved or not.  This is used 
	 * in the updateContent() method to update the title bar and / or close button
	 * to indicate if the document needs saving or not.
	 * 
	 * The default implementation checks AbstractDocument.isChanged(); you can
	 * override this method to change the behaviour.
	 * @return
	 */
	public boolean isDocumentSaved(){
		return !document.isChanged();
	}
	
	public StandardDocument getDocument(){
		return document;
	}
}