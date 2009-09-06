/*
 * Created on Jul 11, 2006 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.ComboBoxModel;
import javax.swing.text.JTextComponent;

import ca.digitalcave.moss.swing.model.DefaultSortedSetComboBoxModel;

/**
 * A combo box which includes a hint (text which appears when the widget
 * is empty, until the user clicks on it).
 * 
 * @author wyatt
 *
 */
public class MossHintComboBox extends MossScrollingComboBox implements StandardHintTextComponent {
	public static final long serialVersionUID = 0;

	private String hint;

	public MossHintComboBox(String hint){
		this(new DefaultSortedSetComboBoxModel(), hint);
	}

	public MossHintComboBox(ComboBoxModel model, String hint){
		super(model);
		this.setEditable(true);
		this.hint = hint;

		getTextComponent().addFocusListener(new FocusAdapter(){
			@Override
			public void focusGained(FocusEvent arg0) {
				MossHintComboBox.this.setPopupVisible(true);
				showHint();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				MossHintComboBox.this.setPopupVisible(false);
				showHint();
			}
		});
	}


	private void showHint(){
		JTextComponent editor = getTextComponent();
		if (!editor.isFocusOwner() && getText().equals("")){
			if (editor.getText().equals("")){
				editor.setText(this.hint);
				setForeground(Color.GRAY);
			}
			else {
				setSelectedItem(editor.getText());
				setForeground(Color.BLACK);
			}
		}
		else {
			if (editor.getText().equals(this.hint))
				editor.setText("");

			setForeground(Color.BLACK);
		}
	}
	
	private JTextComponent getTextComponent(){
		if (getEditor() == null)
			return null;
		return (JTextComponent) getEditor().getEditorComponent();
	}
	
	public String getText(){
		if (getTextComponent().getText().equals(hint))
			return "";
		return getTextComponent().getText();
	}
	
	public void setText(String value) {
		if (value == null)
			value = "";
		getTextComponent().setText(value);
		
		if (value.equals("") && !this.isFocusOwner())
			showHint();
		else
			showHint();
	}
	
	public boolean isHintShowing(){
		return (this.getText().equals(""));
	}
	
	@Override
	public void setForeground(Color fg) {
		if (getTextComponent() != null)
			getTextComponent().setForeground(fg);
		super.setForeground(fg);
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}
}
