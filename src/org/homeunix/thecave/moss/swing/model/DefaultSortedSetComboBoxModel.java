/*
 * Created on Jul 3, 2007 by wyatt
 */
package org.homeunix.thecave.moss.swing.model;

import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

/**
 * @author wyatt
 *
 * Default model for the JHintComboBox.  Enforces a 'set' type interface (no duplicates).
 * Keeps the list sorted at all times (thus overriding insertElementAt() with addElement).
 */
public class DefaultSortedSetComboBoxModel extends DefaultComboBoxModel {
	public static final long serialVersionUID = 0;

	public DefaultSortedSetComboBoxModel() {
//		this.addElement("");	//We need one null for auto complete
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addElement(Object arg0) {
		for (int i = 0; i < this.getSize(); i++){
			if (this.getElementAt(i).equals(arg0))
				return; //Object already in list
		}
			
		addElementInternal(arg0);
		
		//Sort the entries - TODO should only do this if the objects implement Comparable.
		Vector entries = new Vector();
		for (int i = 0; i < this.getSize(); i++){
			entries.add(this.getElementAt(i));
		}
		
		Collections.sort(entries);
		this.removeAllElements();
		for (Object o : entries) {
			this.addElementInternal(o);	
		}

	}
	
	private void addElementInternal(Object arg0){
		super.addElement(arg0);
	}

	@Override
	public void insertElementAt(Object arg0, int arg1) {
		addElement(arg0);
	}
}
