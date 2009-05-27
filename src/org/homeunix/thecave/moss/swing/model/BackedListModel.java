package org.homeunix.thecave.moss.swing.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;

/**
 * A list model implementation which allows you to pass in an existing
 * list as a backing to the list model.  The list model will automatically
 * reflect changes in the backing list (although you may need to fire
 * a list change event for the list itself to update).
 * 
 * @author wolson
 *
 * @param <T>
 */
public class BackedListModel<T> extends AbstractListModel implements Collection<T> {
	public static final long serialVersionUID = 0; 
	
	protected final List<T> listModel;
	
	public BackedListModel() {
		this(null);
	}
	
	public BackedListModel(List<T> listModel) {
		if (listModel != null)
			this.listModel = listModel;
		else
			this.listModel = new LinkedList<T>();
	}

	
	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	public Object getElementAt(int index) {
		return listModel.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	public int getSize() {
		return listModel.size();
	}

	
	public void fireListChanged(){
		this.fireContentsChanged(this, -1, -1);
	}
	
	public boolean add(T arg0) {
		boolean r = listModel.add(arg0);
		this.fireContentsChanged(this, -1, -1);
		return r;
	}

	public boolean addAll(Collection<? extends T> arg0) {
		boolean r = listModel.addAll(arg0);
		this.fireContentsChanged(this, -1, -1);
		return r;
	}

	public void clear() {
		listModel.clear();
		this.fireContentsChanged(this, -1, -1);
	}

	public boolean contains(Object arg0) {
		return listModel.contains(arg0);
	}

	public boolean containsAll(Collection<?> arg0) {
		return listModel.contains(arg0);
	}

	public boolean isEmpty() {
		return listModel.isEmpty();
	}

	public Iterator<T> iterator() {
		return listModel.iterator();
	}

	public boolean remove(Object arg0) {
		boolean r = listModel.remove(arg0);
		this.fireContentsChanged(this, -1, -1);
		return r;
	}

	public boolean removeAll(Collection<?> arg0) {
		boolean r = listModel.removeAll(arg0);
		this.fireContentsChanged(this, -1, -1);
		return r;
	}

	public boolean retainAll(Collection<?> arg0) {
		boolean r = listModel.retainAll(arg0);
		this.fireContentsChanged(this, -1, -1);
		return r;
	}

	public int size() {
		return getSize();
	}

	public Object[] toArray() {
		return listModel.toArray();
	}

	@SuppressWarnings("hiding")
	public <T> T[] toArray(T[] arg0) {
		return listModel.toArray(arg0);
	}
}
