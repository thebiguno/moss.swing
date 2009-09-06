/*
 * Created on Aug 9, 2007 by wyatt
 */
package ca.digitalcave.moss.swing;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * This is a class which allows for keeping track of what documents, frames, 
 * etc are open for a single application.  It is automatically opened as a 
 * Singleton object as needed.
 * 
 * @author wyatt
 *
 */
public class ApplicationModel {
	
	private Map<Object, MossFrame> openFrames = new HashMap<Object, MossFrame>();
	private Map<Object, List<MossFrame>> groupedFrames = new HashMap<Object, List<MossFrame>>();	
	
	//Singleton Instance
	public static ApplicationModel getInstance() {
		return SingletonHolder.instance;
	}

	static class SingletonHolder {
		private static ApplicationModel instance = new ApplicationModel();		
	}

	private ApplicationModel() {}

	
	//----openFrames methods-----
	
	boolean isFrameOpen(Object key){
		if (key != null && openFrames.get(key) != null)
			return true;

		return false;
	}
	MossFrame getFrame(Object key){
		return openFrames.get(key);
	}
	void addOpenFrame(Object key, MossFrame frame){
		openFrames.put(key, frame);
	}
	void removeOpenFrame(Object key){
		openFrames.remove(key);
	}
	/**
	 * Get a list of open frames, sorted by title.
	 * @return
	 */
	public List<MossFrame> getOpenFrames(){
		List<MossFrame> values = new LinkedList<MossFrame>(openFrames.values());
		Collections.sort(values, new Comparator<MossFrame>(){
			public int compare(MossFrame o1, MossFrame o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		return Collections.unmodifiableList(values);
	}
	
	//----groupedFrames methods-----
	
	List<MossFrame> getGroupedFrames(Object key){
		return groupedFrames.get(key);
	}
	void addFrameToGroup(MossFrame frame, Object key){
		if (groupedFrames.get(key) == null)
			groupedFrames.put(key, new LinkedList<MossFrame>());
		
		groupedFrames.get(key).add(frame);
	}
	void removeFrameFromGroup(MossFrame frame, Object key){
		if (groupedFrames.get(key) == null)
			groupedFrames.put(key, new LinkedList<MossFrame>());
		
		groupedFrames.get(key).remove(frame);		
	}
}
