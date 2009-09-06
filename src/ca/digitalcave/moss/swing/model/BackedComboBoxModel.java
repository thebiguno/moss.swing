/*
 * Created on Aug 26, 2007 by wyatt
 */
package ca.digitalcave.moss.swing.model;

import java.util.List;

import javax.swing.ComboBoxModel;

public class BackedComboBoxModel<T> extends BackedListModel<T> implements ComboBoxModel {
	public static final long serialVersionUID = 0;
	
	private Object selectedItem;
	
	public BackedComboBoxModel() {
		super();
	}
	
	public BackedComboBoxModel(List<T> backingList) {
		super(backingList);
	}	
	
	public Object getSelectedItem() {
		return selectedItem;
	}
	
	public void setSelectedItem(Object selectedItem) {
		this.selectedItem = selectedItem;
	}
}
