/*
 * Created on Aug 25, 2007 by wyatt
 */
package ca.digitalcave.moss.swing.model;

public interface ValueScrollerModel<T> {
	
	public void incrementValue();
	
	public void decrementValue();
	
	public T getValue();
	
	public String getValueString();
	
	public void setValue(T value);
}
