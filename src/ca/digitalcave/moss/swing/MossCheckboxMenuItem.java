package ca.digitalcave.moss.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.KeyStroke;

/**
 * Copyright assigned to Wyatt Olson as of Nov 20 2008, as per email correspondance from mpeccorini@yahoo.com
 *
 * @author mpeccorini
 * 
 */
public class MossCheckboxMenuItem extends JCheckBoxMenuItem implements StandardMenu, ActionListener {
	public static final long serialVersionUID = 0;

	private final MossFrame frame;

	public MossCheckboxMenuItem(MossFrame frame) {
		this(frame, "");
	}

	public MossCheckboxMenuItem(MossFrame frame, String text) {
		super(text);

		this.frame = frame;
		this.addActionListener(this);
	}

	public MossCheckboxMenuItem(MossFrame frame, String text, KeyStroke shortcut) {
		this(frame, text);

		this.setAccelerator(shortcut);
	}


	public MossFrame getFrame() {
		return frame;
	}

	public void updateMenus() {}

	public void actionPerformed(ActionEvent e) {}
}