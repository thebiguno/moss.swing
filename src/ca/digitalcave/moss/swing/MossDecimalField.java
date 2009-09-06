package ca.digitalcave.moss.swing;



import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JTextField;

import ca.digitalcave.moss.common.OperatingSystemUtil;

/**
 * @author wyatt
 * 
 * A modified JTextField which includes logic to use it for currency
 * input.  Returns a long value, in the max decimal place.
 * By default, 2 is set to be the max decimal place; in this case, 
 * 12345 equals 123.45, as used in Buddi's internal currency representation.
 * If you were to set the maxDecimalPlaces to 4, 12345 would be 1.2345.
 * 
 * Be sure to use the getValue() / setValue() methods to access the value, instead
 * of getText() / setText().
 */
public class MossDecimalField extends JTextField implements KeyListener, FocusListener {

	public static final long serialVersionUID = 0;

	private long value = 0;	//The raw value of the field
	private int decimalPlaces = 0; //How many decimal places are included in the above value?
	private final int maxDecimalPlaces;  //How many decimal places total?  For now, this is hard coded to 2
	private boolean negative = false; //Is the value negative?
	private final boolean allowNegative; // Do we allow negative numbers?

	private boolean decimalEntry = false; //Have we hit the decimal point yet? 

	private NumberFormat decimalFormat;

	/**
	 * Creates a JCurrencyField with a default value of 0.
	 */
	public MossDecimalField() {
		this(0, true, 2);
	}

	/**
	 * Creates a JCurrencyField with the given value.
	 * @param value Value to set
	 */
	public MossDecimalField(long value){
		this(value, true, 2);
	}

	public MossDecimalField(boolean allowNegative){
		this(0, allowNegative, 2);
	}

	/**
	 * Creates a JCurrencyField with the given value, and the specified
	 * number of decimal places.
	 * @param value Value to set
	 * @param maxDecimalPlaces Number of decimal places to show
	 */
	public MossDecimalField(long value, boolean allowNegative, int maxDecimalPlaces){
		super();
		this.allowNegative = allowNegative;
		this.maxDecimalPlaces = maxDecimalPlaces;

		decimalFormat = DecimalFormat.getInstance();
		decimalFormat.setMaximumFractionDigits(maxDecimalPlaces);
		decimalFormat.setMinimumFractionDigits(maxDecimalPlaces);

		this.setValue(value);

		updateText(false);

		this.addKeyListener(this);
		this.addFocusListener(this);		
	}

	/**
	 * Sets the given value to the text box.
	 * @param value
	 */
	public void setValue(long value){
		if (!allowNegative)
			value = Math.abs(value);

		negative = (value < 0);

		if (value != 0){
			decimalEntry = true;
			decimalPlaces = maxDecimalPlaces;
		}
		else if (value == 0){
			decimalEntry = false;
			decimalPlaces = 0;
		}

		this.value = value;
		updateText(false);
	}


	/**
	 * Returns the value of the given text.  Returns a long number, 
	 * representing the specified number of decimal places.  For 
	 * instance, 123.45 is returned as 12345 assuming we have set the
	 * number of decimal places to 2; 12.345 is returned as 12345 if the
	 * number of decimal palces is 3, etc. 
	 * @return The value currently entered in the text box
	 */
	public long getValue(){
		long retValue = Math.abs(value);

		//Adjust the return value for the number of decimal places.
		// We want to return a long, expressed in the lowest
		// unit of the decimal palces.  To do this, we pad
		// with zeros for each unused decimal place, using the formula
		// retValue *- 10 ** (maxDecimalPlaces - decimalPlaces).
		retValue *= Math.pow(10, (maxDecimalPlaces - decimalPlaces));

		if (negative)
			retValue *= -1;
		return retValue;
	}

	private void updateText(boolean keepHighlight){
		int highlightStart = getSelectionStart();
		int highlightEnd = getSelectionEnd();
		
		super.setText(decimalFormat.format(getValue() / Math.pow(10, maxDecimalPlaces)));

		//Move cursor to correct location.  If we are entering a 
		// whole number, it will be just to the left of the decimal
		// point.  If we are entering a decimal, it will be in the 
		// correct location.
		if (decimalPlaces > 0 || maxDecimalPlaces == 0)
			this.setCaretPosition(this.getText().length() - maxDecimalPlaces + decimalPlaces);			
		else {
			if (decimalEntry)
				this.setCaretPosition(this.getText().length() - maxDecimalPlaces);
			else
				this.setCaretPosition(this.getText().length() - maxDecimalPlaces - 1);
		}
		
		if (keepHighlight){
			this.setSelectionStart(highlightStart);
			this.setSelectionEnd(highlightEnd);
		}

		// Uncomment this to provide highlighting.  I don't like this,
		// as it does not follow the regular rules for highlighting,
		// and thus could be confusing.
//		this.setSelectionStart(this.getCaretPosition());
//		this.setSelectionEnd(this.getText().length());
	}
	
	private void setHighlightNone(){
		updateText(false);
	}
	
	private void setHighlightAll(){
		this.setSelectionStart(0);
		this.setSelectionEnd(this.getText().length());
	}

	/**
	 * Tries to set the value based on the given text.  This method
	 * should not be used, as it will produce unpredictable results.
	 * Use setValue(long) instead.
	 */
	public void setText(String text){
		long newValue = 0;
		try {
			newValue = Long.parseLong(text) * (long) Math.pow(10, maxDecimalPlaces);
		}
		catch (NumberFormatException nfe){}

		setValue(newValue);
	}


	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent e) {	
		//Set to true if we handle the event in this class.  We use this to
		// determine if we should fire a new event to the parent container
		// or not.  It's a bit of a hack, but it seems to work better
		// than any alternatives I could find.
		boolean handled = false;
		
		//If the entire field is selected, and we are entering a
		// numberical / decimal / delete key, we will clear it 
		// and allow entry of a new value.
		if (((e.getKeyChar() >= '0' 
			&& e.getKeyChar() <= '9')
			|| e.getKeyChar() == '.'
				|| e.getKeyChar() == ','
					|| e.getKeyCode() == KeyEvent.VK_DELETE 
					|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE)

					&& getSelectionStart() == 0 
					&& getSelectionEnd() == getText().length()){
			negative = false;
			setValue(0);
		}

		//Different logic for each different valid key pressed
		if (e.getKeyChar() >= '0' && e.getKeyChar() <= '9'){
			//Check for long overflow
			if (Math.abs(value) * 10 + 9 >= 0 && (decimalPlaces < maxDecimalPlaces || maxDecimalPlaces == 0)){
				value = (Math.abs(value) * 10 + Long.parseLong(Character.toString(e.getKeyChar()))) * (negative ? -1 : 1);

				if (decimalEntry){
					decimalPlaces++;

					if (decimalPlaces > maxDecimalPlaces)
						decimalPlaces = maxDecimalPlaces;
				}
			}
			else {
				Toolkit.getDefaultToolkit().beep();
			}
			
			setHighlightNone();
			handled = true;
		}
		else if (e.getKeyChar() == '-' && allowNegative && e.getModifiers() == 0){
			negative = !negative;
			handled = true;
			setHighlightNone();
		}
		else if (e.getKeyChar() == '+' && e.getModifiers() == 0){
			negative = false;
			handled = true;
			setHighlightNone();
		}
		else if (e.getKeyChar() == '.'
			|| e.getKeyChar() == ','
				&& e.getModifiers() == 0){
			decimalEntry = true;
			handled = true;
			setHighlightNone();
		}
		else if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_LEFT){
//			if (e.getKeyCode() == KeyEvent.VK_RIGHT)
//				this.setCaretPosition(Math.min(this.getCaretPosition() + 1, this.getText().length() - 1));
//			if (e.getKeyCode() == KeyEvent.VK_LEFT)
//				this.setCaretPosition(Math.max(this.getCaretPosition() - 1, 0));
			setHighlightNone();
			handled = false;
		}
		else if (e.getKeyCode() == KeyEvent.VK_DELETE 
				|| e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
			if (decimalPlaces > 0
					|| !decimalEntry)
				value = value / 10;

			if (decimalPlaces == 0){
				decimalEntry = false;
			}

			if (decimalPlaces > 0)
				decimalPlaces--;

			handled = true;
			setHighlightNone();
		}
		//Highlight All
		else if (e.getKeyCode() == KeyEvent.VK_A && (
				(OperatingSystemUtil.isMac() && e.isMetaDown()) || (!OperatingSystemUtil.isMac() && e.isControlDown()))){
			handled = true;
			this.setHighlightAll();
		}
		//Check for copy / paste
		else if (e.getKeyCode() == KeyEvent.VK_C && (
				(OperatingSystemUtil.isMac() && e.isMetaDown()) || (!OperatingSystemUtil.isMac() && e.isControlDown()))){
			this.copy();
			handled = true;
		}
		else if (e.getKeyCode() == KeyEvent.VK_V && (
				(OperatingSystemUtil.isMac() && e.isMetaDown()) || (!OperatingSystemUtil.isMac() && e.isControlDown()))){
			this.paste();
			handled = true;
		}
		
		updateText(true);
		
		//If we have not handled this, pass the key event on to the 
		// parent, in case it needs to do something (access a menu 
		// shortcut, etc)
		if (!handled)
			this.getParent().dispatchEvent(new KeyEvent(this.getParent(), KeyEvent.KEY_PRESSED, e.getWhen(), e.getModifiers(), e.getKeyCode(), e.getKeyChar()));
		
		//Don't do anything else with the keypress
		e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent e) {
		e.consume();
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	public void keyTyped(KeyEvent e) {
		e.consume();
	}

	public void focusGained(FocusEvent arg0) {
		setHighlightAll();
		updateText(true);
	}

	public void focusLost(FocusEvent e) {
		updateText(false);
	}

	@Override
	public void copy(){
		Clipboard clipboard = getToolkit().getSystemClipboard();
		StringSelection data = new StringSelection(this.getText());
		clipboard.setContents(data, data);
	}

	@Override
	public void paste(){
		Clipboard clipboard = getToolkit().getSystemClipboard();
		Transferable clipData = clipboard.getContents(this);
		String pastedData;
		try {
			pastedData = (String) (clipData.getTransferData(DataFlavor.stringFlavor));
			pastedData = pastedData.replaceAll("[^0-9,\\.]", "");
			double pastedValue = Double.parseDouble(pastedData);
			pastedValue = pastedValue * Math.pow(10, maxDecimalPlaces);
			this.setValue((long) pastedValue);
		} 
		catch (Exception ex) {
			pastedData = ex.toString();

		}		
	}
}

