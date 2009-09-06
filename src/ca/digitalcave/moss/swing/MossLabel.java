package ca.digitalcave.moss.swing;

import javax.swing.JLabel;
import javax.swing.JTextArea;

/**
 * A 'label-like' class which will automatically wrap.  This
 * is implemented via a JTextArea.  HTML does not display
 * in this component.
 * @author wyatt
 *
 */
public class MossLabel extends JTextArea {
	public static final long serialVersionUID = 0;
	
	public MossLabel(){
		this("");
	}
	
	public MossLabel(String text) {
		super(text);
		
		this.setEditable(false);
		this.setBorder(null);
		this.setLineWrap(true);
		this.setWrapStyleWord(true);
		this.setBackground(new JLabel().getBackground());
		this.setForeground(new JLabel().getForeground());
		this.setFont(new JLabel().getFont());
	}
}
