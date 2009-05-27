/*
 * Created on Aug 25, 2007 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.homeunix.thecave.moss.swing.model.ValueScrollerModel;

public class MossValueScroller extends JPanel implements ActionListener {
	public static final long serialVersionUID = 0;

	private final ValueScrollerModel<?> model;
	private final JButton rightButton;
	private final JButton leftButton;
	private final JTextField valueField;
	
	public MossValueScroller(ValueScrollerModel<?> model) {
		this.model = model;
		rightButton = new JButton(">");
		leftButton = new JButton("<");
		valueField = new JTextField();
		valueField.setEditable(false);
		
		valueField.setPreferredSize(new Dimension(200, valueField.getPreferredSize().height));
		rightButton.addActionListener(this);
		leftButton.addActionListener(this);
		
		this.setLayout(new FlowLayout());
		this.add(leftButton);
		this.add(valueField);
		this.add(rightButton);
		
		updateContent();
	}
	
	public void updateContent(){
		valueField.setText(model.getValueString());
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getSource().equals(rightButton)){
			model.incrementValue();
		}
		else if (arg0.getSource().equals(leftButton)){
			model.decrementValue();
		}
		updateContent();
	}
}
