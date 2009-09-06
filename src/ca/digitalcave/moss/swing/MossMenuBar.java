/*
 * Created on Aug 7, 2007 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.Component;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JMenu;
import javax.swing.JMenuBar;


public class MossMenuBar extends JMenuBar implements StandardMenu {
	public static final long serialVersionUID = 0;
	
	private final Collection<StandardMenu> subMenuItems = new LinkedList<StandardMenu>();
	
	private final MossFrame frame;
	
	public MossMenuBar(MossFrame frame) {
		super();
		
		this.frame = frame;
	}
	
	/* (non-Javadoc)
	 * @see org.homeunix.thecave.moss.gui.abstracts.menu.StandardMenu#updateMenus()
	 */
	public void updateMenus() {
		for (StandardMenu item : subMenuItems) {
			item.updateMenus();
		}
	}
	
	@Override
	public JMenu add(JMenu arg0) {
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
	
	@Override
	public void removeAll() {
		super.removeAll();
		subMenuItems.clear();
	}

	public MossFrame getFrame() {
		return frame;
	}
}
