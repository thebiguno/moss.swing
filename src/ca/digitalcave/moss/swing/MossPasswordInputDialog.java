package ca.digitalcave.moss.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

/**
 * Generic password entry dialog.  Can be used to enter a single password,
 * confirm an initial password entry, etc.
 *
 * Initial implementation by John Didion for Buddi encryption system;
 * UI cleanup and extraction from Buddi by Wyatt Olson.
 * 
 * @author Wyatt Olson
 * @author John Didion
 * 
 */
public class MossPasswordInputDialog extends JPanel {
	private static final long serialVersionUID = 0;

	private final String enterPassword;
	private final String enterPasswordTitle;
	private final String password;
	private final String confirmPassword;
	private final String passwordsDontMatch;
	private final String passwordsDontMatchTitle;
	private final String noPasswordEntered;
	private final String noPasswordEnteredTitle;
	private final String ok;
	private final String cancel;

	private String value;

	/**
	 * Create a new password input dialog, with default (English) strings.
	 */
	public MossPasswordInputDialog(){
		this("Please enter password:",
				"Enter Password", "Password", "Confirm Password",
				"Passwords Don't Match", "Error", "No Password Entered",
				"Error", "OK", "Cancel");		
	}
	
	/**
	 * Create a new password input dialog, with custom strings.
	 * @param parent The parent component, used for positioning.
	 * @param enterPassword The main message to show on the form 
	 * @param enterPasswordTitle The dialog title string
	 * @param password The label for first input field
	 * @param confirmPassword The label for the second (confirmation) input field
	 * @param passwordsDontMatch The error message if the passwords don't match
	 * @param passwordsDontMatchTitle The title for passwordsDontMatch
	 * @param noPasswordEntered The error message if there is no password entered
	 * @param noPasswordEnteredTitle The title for noPaswordEntered
	 * @param ok
	 * @param cancel
	 */	public MossPasswordInputDialog(String enterPassword, String enterPasswordTitle, 
			String password, String confirmPassword, String passwordsDontMatch,
			String passwordsDontMatchTitle, String noPasswordEntered, String noPasswordEnteredTitle,
			String ok, String cancel){
		this.enterPassword = enterPassword;
		this.enterPasswordTitle = enterPasswordTitle;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.passwordsDontMatch = passwordsDontMatch;
		this.passwordsDontMatchTitle = passwordsDontMatchTitle;
		this.noPasswordEntered = noPasswordEntered;
		this.noPasswordEnteredTitle = noPasswordEnteredTitle; 
		this.ok = ok;
		this.cancel = cancel;
	}

	/**
	 * Asks user for a password, and returns the result.
	 * @param showConfirm Show the confirm entry field?
	 * @param isEmptyPasswordAllowed Is a blank password allowed?
	 * @return
	 */
	public char[] askForPassword(boolean showConfirm, boolean isEmptyPasswordAllowed) {
		return askForPassword(
				null, 
				showConfirm,
				isEmptyPasswordAllowed
		);
	}

	/**
	 * Asks user for a password, and returns the result.
	 * @param parentComponent The component to center the dialog around
	 * @param showConfirm Show the confirm entry field?
	 * @param isEmptyPasswordAllowed Is a blank password allowed?
	 * @return
	 */
	public char[] askForPassword(Component parentComponent, boolean showConfirm, boolean isEmptyPasswordAllowed) {
		Component parent = (null == parentComponent) ?
				JOptionPane.getRootFrame() : parentComponent;
				this.setComponentOrientation(parent.getComponentOrientation());

				JDialog dialog = this.createDialog(parentComponent, showConfirm, isEmptyPasswordAllowed);
				dialog.setVisible(true);
				dialog.dispose();

				if (this.getValue() != null)
					return this.getValue().toCharArray();
				return null;
	}

	private Window getWindowForComponent(Component component) {
		if (component == null) {
			return JOptionPane.getRootFrame();
		}
		if (component instanceof Frame || component instanceof Dialog) {
			return (Window) component;
		}
		return getWindowForComponent(component.getParent());
	}

	private JDialog createDialog(Component parentComponent, final boolean showConfirm, final boolean isEmptyPasswordAllowed) throws HeadlessException {
		Window window = getWindowForComponent(parentComponent);

		final JDialog dialog = ((window instanceof Frame) ?
				new JDialog((Frame) window, enterPasswordTitle, true) :
					new JDialog((Dialog) window, enterPasswordTitle, true));

		this.setLayout(new GridLayout(0, 1));

		JLabel messageLabel = new JLabel(enterPassword, JLabel.LEFT);
		this.add(messageLabel);

		JPanel passwordPanel1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel pw1Label = new JLabel(password);
		passwordPanel1.add(pw1Label);

		final JPasswordField pw1 = new JPasswordField();
		Dimension passwordBoxSize = new Dimension(200, pw1.getPreferredSize().height);
		pw1.setPreferredSize(passwordBoxSize);
		passwordPanel1.add(pw1);

		this.add(passwordPanel1);

		JPanel passwordPanel2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JLabel pw2Label = new JLabel(confirmPassword);
		passwordPanel2.add(pw2Label);

		final JPasswordField pw2 = new JPasswordField();
		pw2.setPreferredSize(passwordBoxSize);
		passwordPanel2.add(pw2);

		if (showConfirm)
			this.add(passwordPanel2);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton okButton = new JButton(ok);
		okButton.setPreferredSize(new Dimension(Math.max(100, okButton.getPreferredSize().width), okButton.getPreferredSize().height));
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String pw1Value = new String(pw1.getPassword());
				String pw2Value = new String(pw2.getPassword());
				if ((pw1Value == null && pw2Value == null)
						|| pw1Value.equals(pw2Value)
						|| !showConfirm) {
					//The user hit OK without entering password.  If 
					// we have specified that empty passwords are not
					// OK, we will return null. 
					if (pw1Value != null 
							&& pw1Value.length() == 0
							&& !isEmptyPasswordAllowed){
						noPasswordEntered(dialog, isEmptyPasswordAllowed, showConfirm);
					}
					else{
						MossPasswordInputDialog.this.value = pw1Value;
						dialog.setVisible(false);
					}
				} 
				else {
					JOptionPane.showMessageDialog(
							dialog,
							passwordsDontMatch,
							passwordsDontMatchTitle,
							JOptionPane.ERROR_MESSAGE
					);

					pw1.requestFocus();
					pw1.setText("");
					pw2.setText("");

					pw1.select(0, pw1.getPassword().length);
				}
			}			
		});

		final JButton cancelButton = new JButton(cancel);
		cancelButton.setPreferredSize(new Dimension(Math.max(100, cancelButton.getPreferredSize().width), cancelButton.getPreferredSize().height));
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				noPasswordEntered(dialog, isEmptyPasswordAllowed, showConfirm);
			}			
		});
		
		KeyAdapter keyListener = new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
					cancelButton.doClick();
				}
				super.keyPressed(e);
			}
		};
		
		pw1.addKeyListener(keyListener);
		pw2.addKeyListener(keyListener);

		this.setBorder(BorderFactory.createEmptyBorder(7, 17, 17, 17));

		//Macintosh OK buttons should be on the right, and I suppose
		// it's not nice to force this on poor Windows users
		if (System.getProperty("os.name").startsWith("Mac OS")){
			buttonPanel.add(cancelButton);
			buttonPanel.add(okButton);
		}
		else{
			buttonPanel.add(okButton);
			buttonPanel.add(cancelButton);
		}

		this.add(buttonPanel);

		dialog.getRootPane().setDefaultButton(okButton);

		Container contentPane = dialog.getContentPane();
		contentPane.setLayout(new BorderLayout());
		contentPane.add(this, BorderLayout.CENTER);

		dialog.setResizable(false);

		dialog.pack();
		dialog.setLocationRelativeTo(parentComponent);

		WindowAdapter adapter = new WindowAdapter() {            
			public void windowClosing(WindowEvent we) {
				noPasswordEntered(dialog, isEmptyPasswordAllowed, showConfirm);
			}
		};
		dialog.addWindowListener(adapter);
		dialog.addWindowFocusListener(adapter);        

		return dialog;
	}

	private void noPasswordEntered(JDialog dialog, boolean isEmptyPasswordAllowed, boolean isConfirmShowing){
		if (!isEmptyPasswordAllowed && isConfirmShowing)
			JOptionPane.showMessageDialog(
					null, 
					noPasswordEntered, 
					noPasswordEnteredTitle, 
					JOptionPane.INFORMATION_MESSAGE
			);
		MossPasswordInputDialog.this.value = null;
		dialog.setVisible(false);
	}

	private String getValue() {
		return this.value;
	}
}
