package org.homeunix.thecave.moss.swing;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * A simple extension of a JComboBox which enables the scrolling behaviour
 * by default (on a regular JComboBox, you have to manually enable it).
 * It is simple enough to probably not deserve its own class, but I seem 
 * to use it enough to warrant doing so. 
 * 
 * @author wyatt
 *
 */
public class MossScrollingComboBox extends JComboBox {
	public static final long serialVersionUID = 0;
	
	private static int DEFAULT_MAX_ROW_COUNT = 15;

	public MossScrollingComboBox(){
		this(DEFAULT_MAX_ROW_COUNT);
	}
	
	public MossScrollingComboBox(int maxRowCount){
		super();
		this.setMaximumRowCount(maxRowCount);
	}
	
	public MossScrollingComboBox(Object[] arg0){
		super(arg0);
		this.setMaximumRowCount(DEFAULT_MAX_ROW_COUNT);
	}
	
	public MossScrollingComboBox(Vector<?> elements){
		super(elements);
		this.setMaximumRowCount(DEFAULT_MAX_ROW_COUNT);
	}
	
	public MossScrollingComboBox(ComboBoxModel model){
		super(model);
		this.setMaximumRowCount(DEFAULT_MAX_ROW_COUNT);
	}
	
	public static void setDefaultMaxRowCount(int maxRowCount){
		DEFAULT_MAX_ROW_COUNT = maxRowCount;
	}
	
	public static int getDefaultMaxRowCount(){
		return DEFAULT_MAX_ROW_COUNT;
	}
}

