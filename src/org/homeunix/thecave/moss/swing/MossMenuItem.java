/*
 * Created on Aug 7, 2007 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.KeyStroke;


public class MossMenuItem extends JMenuItem implements StandardMenu, ActionListener {
	public static final long serialVersionUID = 0;
	
	private final MossFrame frame;
	
	public MossMenuItem(MossFrame frame) {
		this(frame, "");
	}
	
	public MossMenuItem(MossFrame frame, String text) {
		super(text);
		
		this.frame = frame;
		this.addActionListener(this);
	}
	
	public MossMenuItem(MossFrame frame, String text, KeyStroke shortcut) {
		this(frame, text);

		this.setAccelerator(shortcut);
	}
	

	public MossFrame getFrame() {
		return frame;
	}
	
	public void actionPerformed(ActionEvent e) {}
	
	public void updateMenus() {}
}
