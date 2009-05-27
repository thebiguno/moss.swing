/*
 * Created on May 21, 2006 by wyatt
 */
package org.homeunix.thecave.moss.swing;


/**
 * An interface for all Hint text components.
 * 
 * @author wyatt
 *
 */
public interface StandardHintTextComponent {
	
	/**
	 * Is the hint showing, or has text been entered?
	 * @return
	 */
	public boolean isHintShowing();
	
	/**
	 * Returns the hint text associated with the item.  The hint is the
	 * text which displays when there is nothing yet entered into the
	 * text component.
	 * @return
	 */
	public String getHint();
	
	
	/**
	 * Sets the hint text associated with the item.  The hint is the text
	 * which is to display when there is nothing yet entered into the
	 * text component.
	 * @param hint
	 */
	public void setHint(String hint);
	
	
	/**
	 * Gets the text value.
	 * @return
	 */
	public String getText();
	
	/**
	 * Sets the text value.
	 * @param text
	 */
	public void setText(String text);
}
