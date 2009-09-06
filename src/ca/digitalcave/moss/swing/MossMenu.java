/*
 * Created on Aug 7, 2007 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.Component;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JMenuItem;


public class MossMenu extends JMenu implements StandardMenu {
	public static final long serialVersionUID = 0;
	
	private final Collection<StandardMenu> subMenuItems = new LinkedList<StandardMenu>();

	private final MossFrame frame; 
	
	public MossMenu(MossFrame frame) {
		this(frame, null);
	}
	
	public MossMenu(MossFrame frame, String text) {
		super(text);
		this.frame = frame;
	}	
	
	/* (non-Javadoc)
	 * @see org.homeunix.thecave.moss.gui.abstracts.menu.StandardMenu#updateMenus()
	 */
	public void updateMenus() {
		this.setEnabled(getItemCount() > 0);
		
		for (StandardMenu item : subMenuItems) {
			item.updateMenus();
		}
	}

	
	@Override
	public JMenuItem add(JMenuItem arg0) {
		if (arg0 instanceof StandardMenu){
			subMenuItems.add((StandardMenu) arg0);
		}

		return super.add(arg0);
	}
	
	@Override
	public void remove(Component comp) {
		if (comp instanceof StandardMenu){
			subMenuItems.add((StandardMenu) comp);
		}

		super.remove(comp);
	}

	public MossFrame getFrame() {
		return frame;
	}
}
