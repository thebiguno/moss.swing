/*
 * Created on Jul 11, 2006 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;

/**
 * A text field which includes a hint (text which appears when the widget
 * is empty, until the user clicks on it).
 * 
 * @author wyatt
 *
 */
public class MossHintTextField extends JTextField implements StandardHintTextComponent {
	public static final long serialVersionUID = 0;

	private String hint;

	public MossHintTextField(String hint){
		this.hint = hint;
		showHint();

		this.addFocusListener(new FocusAdapter(){
			@Override
			public void focusGained(FocusEvent arg0) {
				showHint();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				showHint();
			}
		});
	}
	
	private void showHint(){
		if (this.isFocusOwner() || !this.getText().equals("")){
			if (super.getText().equals(this.hint))
				super.setText("");
			
			setForeground(Color.BLACK);
		}
		else {
			super.setText(this.hint);
			setForeground(Color.GRAY);
		}
	}

	public boolean isHintShowing(){
		return (!this.getForeground().equals(Color.BLACK));
	}
	
	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}
	
	@Override
	public String getText() {
		if (super.getText().equals(hint))
			return "";
		return super.getText();
	}
	
	@Override
	public void setText(String arg0) {
		super.setText(arg0);
		
		showHint();
	}
}
