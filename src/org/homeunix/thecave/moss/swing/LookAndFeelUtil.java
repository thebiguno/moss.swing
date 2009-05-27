/*
 * Created on Aug 14, 2007 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.util.logging.Logger;

import javax.swing.UIManager;

import org.homeunix.thecave.moss.common.OperatingSystemUtil;

/**
 * Simplifies the setting of Java Look and Feels.  Call the method without 
 * arguments to set a sane default, based on platform and available classes.
 * Call the method with a class name argument, and it will attempt to set 
 * the defined LnF, or revert to default if there are problems.
 * 
 * @author wyatt
 *
 */
public class LookAndFeelUtil {

	public static final String QUAQUA = "ch.randelshofer.quaqua.QuaquaLookAndFeel";
	public static final String WINLAF = "net.java.plaf.windows.WindowsLookAndFeel";
	
	/**
	 * Tries to set a good look and feel based on the operating system and available 
	 * LnF classes.  Will try to use Quaqua for OS X, and WinLAF for Windows;
	 * if these fail, it will revert to the System look and feel or (finally)
	 * the Cross Platform look and feel.
	 */
	public static void setLookAndFeel(){
		setLookAndFeel(null);
	}

	/**
	 * Tries to set the look and feel based on the provided class name.  If this 
	 * fails (or if the className is null), it will fall back to the same LnF 
	 * set in setLookAndFeel().
	 * @param className
	 */
	public static void setLookAndFeel(String className){
		if (setLookAndFeelInternal(className))
			return;
		
		if (OperatingSystemUtil.isMac()){
			System.setProperty("Quaqua.tabLayoutPolicy", "scroll");
			System.setProperty("Quaqua.selectionStyle", "bright");
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
			System.setProperty("apple.awt.rendering", "VALUE_RENDER_SPEED"); // VALUE_RENDER_SPEED or VALUE_RENDER_QUALITY
			System.setProperty("apple.awt.interpolation", "VALUE_INTERPOLATION_NEAREST_NEIGHBOR"); // VALUE_INTERPOLATION_NEAREST_NEIGHBOR, VALUE_INTERPOLATION_BILINEAR, or VALUE_INTERPOLATION_BICUBIC
			System.setProperty("apple.awt.showGrowBox", "false");
			System.setProperty("com.apple.mrj.application.growbox.intrudes","true");
			
			if (setLookAndFeelInternal(QUAQUA))
				return;
		}
		if (OperatingSystemUtil.isWindows()){
			//Include required properties here.
			if (setLookAndFeelInternal(WINLAF))
				return;
		}
		if (setLookAndFeelInternal(UIManager.getSystemLookAndFeelClassName()))
			return;
		if (setLookAndFeelInternal(UIManager.getCrossPlatformLookAndFeelClassName()))
			return;
		
		Logger.getLogger(LookAndFeelUtil.class.getName()).warning("Unable to set any look and feel");
	}

	private static boolean setLookAndFeelInternal(String className){
		try {
			UIManager.installLookAndFeel(className, className);
			UIManager.setLookAndFeel(className);
						
			return true;
		}
		catch (RuntimeException re){}
		catch (Exception e){}
		
		return false;
	}
}
