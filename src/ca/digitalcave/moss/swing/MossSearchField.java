/*
 * Created on Oct 5, 2006 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.EventListener;
import java.util.EventObject;

import javax.swing.event.EventListenerList;

import org.homeunix.thecave.moss.common.OperatingSystemUtil;

/**
 * A simple search field, similar in layout to the NSSearchField on the Macintosh.
 * When used with Quaqua, it looks quite good on the Mac, and is very close to
 * the native functionality.
 * 
 * @author wyatt
 *
 */
public class MossSearchField extends MossHintTextField {
	public static final long serialVersionUID = 0;

	private final int DIST_FROM_RIGHT_BORDER = (OperatingSystemUtil.isMac() ? 7 : 3);
	private final int SIZE = 15;
	private final int LINE_SIZE_DIFFERENCE = 5;	//The amount from the top left where the X starts
	private final boolean IS_MAC = OperatingSystemUtil.isMac(); //Only show the magnifying glass on a Mac...

	public MossSearchField() {
		this("");
	}
	
	public MossSearchField(String hint){
		super(hint == null ? "" : hint);

		this.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					cancelButton();
				}
				else {
					changed();
				}
			}
		});

		//If we're on a Mac with Quaqua, this will look nice.
		this.putClientProperty("Quaqua.TextField.style","search");

		this.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e) {
				if (getCircleX() <= e.getX() && getCircleX() + SIZE >= e.getX()
						&& getCircleY() <= e.getY() && getCircleY() + SIZE >= e.getY())
					MossSearchField.this.cancelButton();
			}
		});
	}

//	public String getText(){
//		if (this.isHintShowing())
//			return "";
//		else
//			return super.getText();
//	}

	@Override
	public void paint(Graphics oldGraphics) {
		super.paint(oldGraphics);

		Graphics2D g = (Graphics2D) oldGraphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		if (this.getText().length() > 0) {
			//Draw the circle

			final int circleX = getCircleX();
			final int circleY = getCircleY();
			g.setColor(new Color(182, 182, 182)); //Same grey as on the Mac.
			
			g.fillOval(circleX, circleY, SIZE, SIZE);

			//Draw the X

			final int lineOffset = SIZE - LINE_SIZE_DIFFERENCE;
			final int lineX1 = circleX + lineOffset;
			final int lineY1 = circleY + lineOffset;

			final int lineX2 = circleX + SIZE - lineOffset;
			final int lineY2 = circleY + SIZE - lineOffset;

			g.setColor(Color.WHITE);
			g.setStroke(new BasicStroke(1.5f));
			g.drawLine(lineX1, lineY1, lineX2, lineY2);
			g.drawLine(lineX1, lineY2, lineX2, lineY1);
		}
		if (IS_MAC && !this.isFocusOwner() && this.getHint().length() == 0 && this.getText().length() == 0){
			g.setColor(new Color(128, 128, 128));
			g.setStroke(new BasicStroke(3.0f));
			g.drawOval(8, 8, 8, 8);
			g.drawLine(16, 16, 20, 20);
		}
	}

	private int getCircleX(){
		return this.getWidth() - SIZE - DIST_FROM_RIGHT_BORDER;
	}


	private int getCircleY(){
		return (this.getHeight() - 1 - SIZE) / 2 + 1;
	}

	private void cancelButton(){
		this.setText("");
		fireSearchTextChangedEvent(new SearchTextChangedEvent(MossSearchField.this));
	}

	private void changed(){
		fireSearchTextChangedEvent(new SearchTextChangedEvent(MossSearchField.this));
	}

	public class SearchTextChangedEvent extends EventObject {
		public static final long serialVersionUID = 0;

		public SearchTextChangedEvent(Object source) {
			super(source);
		}
	}

	public interface SearchTextChangedEventListener extends EventListener {
		public void searchTextChangedEventOccurred(SearchTextChangedEvent evt);
	}

	// Create the listener list
	protected EventListenerList listenerList =
		new EventListenerList();

	// This methods allows classes to register for MyEvents
	public void addSearchTextChangedEventListener(SearchTextChangedEventListener listener) {
		listenerList.add(SearchTextChangedEventListener.class, listener);
	}

	// This methods allows classes to unregister for MyEvents
	public void removeSearchTextChangedEventListener(SearchTextChangedEventListener listener) {
		listenerList.remove(SearchTextChangedEventListener.class, listener);
	}

	// This private class is used to fire MyEvents.  It also forces an update to the painting.
	private void fireSearchTextChangedEvent(SearchTextChangedEvent evt) {
		this.repaint();

		Object[] listeners = listenerList.getListenerList();
		// Each listener occupies two elements - the first is the listener class
		// and the second is the listener instance
		for (int i=0; i<listeners.length; i+=2) {
			if (listeners[i]==SearchTextChangedEventListener.class) {
				((SearchTextChangedEventListener)listeners[i+1]).searchTextChangedEventOccurred(evt);
			}
		}
	}
}
