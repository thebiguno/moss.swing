/*
 * Created on Feb 3, 2007 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.SwingConstants;


public class MossStatusDialog extends MossDialog {
	public final static long serialVersionUID = 0;
	
	private final JLabel messageLabel;
	
	public MossStatusDialog(String message){
		super((MossFrame) null, false);
		messageLabel = new JLabel(message);
	}
	
	public MossStatusDialog(MossDialog dialog, String message){
		super(dialog, false);		
		messageLabel = new JLabel(message);
	}
	
	public MossStatusDialog(MossFrame frame, String message){
		super(frame, false);
		messageLabel = new JLabel(message);	
	}
	
	public void init() {	
		this.setUndecorated(true);
//		this.setAlwaysOnTop(true);
		
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.setLayout(new BorderLayout());
		this.add(messageLabel, BorderLayout.CENTER);
	}
	
	public void updateButtons() {}
	
	public void updateContent() {}
	
	public void setMessage(String message){
		messageLabel.setText(message);
	}
	
	public String getMessage(){
		return messageLabel.getText();
	}
}
