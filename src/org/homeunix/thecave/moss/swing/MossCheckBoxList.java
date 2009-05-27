/*
 * Created on Oct 7, 2006 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

/**
 * A JList extension which uses a JCheckBox as a cell renderer.  Includes
 * some custom behaviour to make it feel more like a check box selection.
 * 
 * TODO Add a wrapper around the model to allow two selections - the checkbox,
 * as well as a regular list-style selection.
 * 
 * @author wyatt
 *
 */
public class MossCheckBoxList extends JList {
	public static final long serialVersionUID = 0;
	
	public MossCheckBoxList() {
		super();

		final JCheckBoxListCellRenderer renderer = new JCheckBoxListCellRenderer();
		final JCheckBoxListSelectionModel selection = new JCheckBoxListSelectionModel();
		
		this.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.setCellRenderer(renderer);
		this.setSelectionModel(selection);
		
		// We need to remove a bunch of mouse listeners to make the
		// selections work how they should.
		for (MouseListener l : this.getMouseListeners()) {
			this.removeMouseListener(l);
		}
		for (MouseMotionListener l : this.getMouseMotionListeners()){
			this.removeMouseMotionListener(l);
		}
		
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent arg0) {
				selection.toggleSelection(MossCheckBoxList.this.locationToIndex(arg0.getPoint()));				
				super.mouseClicked(arg0);
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {}
			
			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
	}
	
	public MossCheckBoxList(ListModel arg0){
		this();
		this.setModel(arg0);
	}
	
	public class JCheckBoxListCellRenderer extends JCheckBox implements ListCellRenderer {
		public static final long serialVersionUID = 0;
				
		public Component getListCellRendererComponent(JList list, Object obj, int index, boolean isSelected, boolean cellHasFocus) {
			this.setText(obj.toString());
			
			this.setSelected(isSelected);
			
			return this;
		}
	}
	
	public class JCheckBoxListSelectionModel extends DefaultListSelectionModel {
		public static final long serialVersionUID = 0;
		
		public void toggleSelection(int index){
			if (isSelectedIndex(index)){
				removeSelectionInterval(index, index);
			}
			else {
				addSelectionInterval(index, index);
			}
		}
	}
}
