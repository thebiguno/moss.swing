/*
 * Created on Aug 14, 2007 by wyatt
 */
package ca.digitalcave.moss.swing.model;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

public class DefaultGenericListModel<T> extends AbstractListModel {
	public static final long serialVersionUID = 0;

	private final List<T> backingList = new LinkedList<T>(); 
		
	public void addElement(T value){
		backingList.add(value);
		fireContentsChanged(this, -1, -1);
	}
	
	public void addAllElements(Collection<T> values){
		backingList.addAll(values);
		fireContentsChanged(this, -1, -1);
	}
	
	public boolean removeElement(T value){
		boolean r = backingList.remove(value);
		fireContentsChanged(this, -1, -1);
		return r;
	}
	
	public boolean removeAllElements(Collection<T> values){
		boolean r = backingList.removeAll(values);
		fireContentsChanged(this, -1, -1);
		return r;
	}
	
	public boolean retainAllElements(Collection<T> values){
		boolean r = backingList.retainAll(values);
		fireContentsChanged(this, -1, -1);
		return r;
	}
	
	public void clear(){
		backingList.clear();
		fireContentsChanged(this, -1, -1);
	}
	
	public T getElementAt(int index) {
		return backingList.get(index);
	}

	public int getSize() {
		return backingList.size();
	}

	/**
	 * Get an unmodifiable link to the backing list.  This allows you to access
	 * the list in a foreach loop, among other List-only functions.
	 * @return
	 */
	public List<T> getList(){
		return Collections.unmodifiableList(backingList);
	}
}
