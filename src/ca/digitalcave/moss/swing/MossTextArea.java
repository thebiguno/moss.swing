/*
 * Created on Jul 11, 2006 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTextArea;

/**
 * A text field which includes a hint (text which appears when the widget
 * is empty, until the user clicks on it).
 * 
 * @author wyatt
 *
 */
public class MossTextArea extends JTextArea {
	public static final long serialVersionUID = 0;

	private boolean allowTab;
	private KeyAdapter allowTabAdapter = new KeyAdapter(){
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_TAB){
				if ((e.getModifiersEx() & KeyEvent.SHIFT_DOWN_MASK) == 0) //Shift not down
					MossTextArea.this.transferFocus();
				else
					MossTextArea.this.transferFocusBackward();
				e.consume();
			}
		}
	};
	
	/**
	 * Creates a new MossTextArea, with allowTab set to false.
	 */
	public MossTextArea() {
		this(false);
	}

	/**
	 * Creates a new MossTextArea, and sets allowTab as directed.
	 * @param allowTab Should tab enter as text, or switch focus?  If true, we enter it as 
	 * text; if false, we switch focus. 
	 */
	public MossTextArea(boolean allowTab){
		setAllowTab(allowTab);
	}
	
	/**
	 * Sets whether or not this text area allows the tab character, or
	 * if it transfers control to the next component.
	 * @param allowTab
	 */
	public void setAllowTab(boolean allowTab){
		this.allowTab = allowTab;
		
		if (allowTab)
			this.removeKeyListener(allowTabAdapter);
		else
			this.addKeyListener(allowTabAdapter);
	}
	
	public boolean isAllowTab(){
		return allowTab;
	}
}
