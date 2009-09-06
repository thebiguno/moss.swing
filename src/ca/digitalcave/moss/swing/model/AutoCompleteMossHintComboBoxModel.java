/*
 * Created on Aug 19, 2007 by wyatt
 */
package ca.digitalcave.moss.swing.model;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.text.JTextComponent;

import ca.digitalcave.moss.collections.FilteredList;
import ca.digitalcave.moss.swing.MossHintComboBox;

public class AutoCompleteMossHintComboBoxModel<T> extends AbstractListModel implements ComboBoxModel {
	public static final long serialVersionUID = 0;

	private final FilteredList<T> filteredList;
//	private String text;
	private final JTextComponent editor;
	private final MossHintComboBox comboBox;
	private T selectedItem;

	public AutoCompleteMossHintComboBoxModel(final MossHintComboBox comboBox, List<T> backingList) {
		this.filteredList = new FilteredBackingList(backingList);
		editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
		this.comboBox = comboBox;
		
		editor.addFocusListener(new FocusListener(){
			public void focusGained(FocusEvent e) {
				String text = comboBox.getText();
				setSelectedItem(null);
				updateModel();
				comboBox.setText(text);
				comboBox.setPopupVisible(false);
				comboBox.setPopupVisible(true);
//				editor.setSelectionStart(0);
//				editor.setSelectionEnd(editor.getText().length());
			}
			public void focusLost(FocusEvent e) {
				comboBox.setPopupVisible(false);
			}
		});

		editor.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					if (comboBox.getSelectedItem() != null)
						editor.setText(comboBox.getSelectedItem().toString());
				}
				
				super.keyPressed(e);
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() != KeyEvent.VK_ESCAPE
						&& e.getKeyCode() != KeyEvent.VK_LEFT
						&& e.getKeyCode() != KeyEvent.VK_RIGHT
						&& e.getKeyCode() != KeyEvent.VK_UP
						&& e.getKeyCode() != KeyEvent.VK_DOWN
						&& e.getKeyCode() != KeyEvent.VK_HOME
						&& e.getKeyCode() != KeyEvent.VK_END
						&& e.getKeyCode() != KeyEvent.VK_SHIFT
						&& e.getKeyCode() != KeyEvent.VK_ALT
						&& e.getKeyCode() != KeyEvent.VK_CONTROL
						&& e.getKeyCode() != KeyEvent.VK_META
						&& e.getKeyCode() != KeyEvent.VK_CANCEL) {
					String text = editor.getText();
					updateModel();
					editor.setText(text);

					//Cycle popup to refresh size
					comboBox.setPopupVisible(false);
					comboBox.setPopupVisible(true);
				}
				else
					super.keyReleased(e);
			}
		});
	}

	public Object getSelectedItem() {
		filteredList.updateFilteredList();
		if (filteredList.contains(selectedItem))
			return selectedItem;
		return null;
	}

	@SuppressWarnings("unchecked")
	public void setSelectedItem(Object arg0) {
		filteredList.updateFilteredList();
		if (arg0 == null || filteredList.contains(arg0))
			selectedItem = (T) arg0;
	}

	public Object getElementAt(int arg0) {
		return filteredList.get(arg0);
	}

	public int getSize() {
		return filteredList.size();
	}

	private void updateModel(){
		filteredList.updateFilteredList();

		fireContentsChanged(this, -1, -1);
	}

	private class FilteredBackingList extends FilteredList<T> {

		public FilteredBackingList(List<T> backingList) {
			super(backingList);
		}

		@Override
		public boolean isIncluded(T object) {
			if (object == null || comboBox.getText() == null || comboBox.getText().length() == 0)
				return true;

			if (object.toString().toLowerCase().startsWith(comboBox.getText().toLowerCase()))
				return true;

			return false;
		}
	}
}
