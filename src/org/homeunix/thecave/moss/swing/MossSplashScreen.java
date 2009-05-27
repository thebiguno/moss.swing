/*
 * Created on Sep 24, 2007 by wyatt
 */
package org.homeunix.thecave.moss.swing;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A splash screen which will display the given image until the main program starts.
 * If you use MossFrames exclusively, this is automatic; if you don't (or if there
 * are dialogs which may pop up before the MossFrame), you need to manually
 * cancel the splash screen.
 *   
 * @author wyatt
 *
 */
public class MossSplashScreen {
	public static final long serialVersionUID = 0;
	
	private static boolean displaySplash = true;
	private static Frame splashFrame;
	
	/**
	 * Shows the splash screen with the given resource path, describing an image.
	 * @param resource The resource path to an image.  Do not supply the leading
	 * slash.  This will first check on the local filesystem (in the current
	 * working directory), and then will check in the classpath.
	 */
	public static void showSplash(String resource){
		if (displaySplash){
//			final Image img = ClassLoaderFunctions.getImageFromClasspath(resource); 
			splashFrame = new Frame(){
				public static final long serialVersionUID = 0;
				
				@Override
				public void paint(Graphics g) {
					super.paint(g);
					
//					g.drawImage(img, 0, 0, this);
				}
			};
			
			final Timer t = new Timer();
			t.schedule(new TimerTask(){
				@Override
				public void run() {
					if (!displaySplash && ApplicationModel.getInstance().getOpenFrames().size() > 0){
						hideSplash();
						t.cancel();
					}
				}
			}, 100, 100);
			
//			splashFrame.setPreferredSize(new Dimension(img.getWidth(splashFrame), img.getHeight(splashFrame)));
			splashFrame.setUndecorated(true);
			splashFrame.pack();
			splashFrame.setLocationRelativeTo(null);
			splashFrame.setVisible(true);
		}
	}
	
	public static void hideSplash(){
		displaySplash = false;
		if (splashFrame != null){
			splashFrame.setVisible(false);
			splashFrame.dispose();
		}
	}
}
