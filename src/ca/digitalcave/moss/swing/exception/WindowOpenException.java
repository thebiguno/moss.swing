/*
 * Created on Aug 3, 2007 by wyatt
 */
package ca.digitalcave.moss.swing.exception;

public class WindowOpenException extends Exception {
	public static final long serialVersionUID = 0;
	
	public WindowOpenException() {
		super();
	}
	
	public WindowOpenException(String message){
		super(message);
	}
	
	public WindowOpenException(String message, Throwable cause){
		super(message, cause);
	}
	
	public WindowOpenException(Throwable cause){
		super(cause);
	}
}
