/*
 * Created on Feb 3, 2007 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;


public class MossProgressDialog extends MossDialog {
	public final static long serialVersionUID = 0;
	
	private final JProgressBar progressBar;
	private final JLabel messageLabel;
	
	public MossProgressDialog(String title, String message, int minValue, int maxValue, boolean modal){
		super((MossFrame) null, modal);
		
		
		this.setTitle(title);
		messageLabel = new JLabel(message);
		progressBar = new JProgressBar(minValue, maxValue);

	}
	
	public MossProgressDialog(MossDialog dialog, String title, String message, int minValue, int maxValue, boolean modal){
		super(dialog, modal);
		
		this.setTitle(title);
		messageLabel = new JLabel(message);
		progressBar = new JProgressBar(minValue, maxValue);
	}
	
	public MossProgressDialog(MossFrame frame, String title, String message, int minValue, int maxValue, boolean modal){
		super(frame, modal);

		this.setTitle(title);
		messageLabel = new JLabel(message);	
		progressBar = new JProgressBar(minValue, maxValue);
	}
	
	public void init() {
		JPanel progressBarPanel = new JPanel(new BorderLayout());
		progressBarPanel.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createTitledBorder((String) null),
						BorderFactory.createEmptyBorder(3, 5, 5, 5)
				)
		);
		progressBarPanel.add(progressBar, BorderLayout.CENTER);
		
		this.setUndecorated(true);
//		this.setAlwaysOnTop(true);
//		this.getRootPane().setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));
		this.setLayout(new BorderLayout());
		this.add(messageLabel, BorderLayout.NORTH);
		this.add(progressBarPanel, BorderLayout.CENTER);
	}
		
	public void setValue(int value){
		if (value >= getMin() && value <= getMax()){
			progressBar.setIndeterminate(false);
			progressBar.setValue(value);
		}
		else
			progressBar.setIndeterminate(true);
	}
	
	public void setMessage(String message){
		messageLabel.setText(message);
	}
	
	public int getValue(){
		return progressBar.getValue();
	}
	
	public int getMin(){
		return progressBar.getMinimum();
	}
	
	public int getMax(){
		return progressBar.getMaximum();
	}
}
