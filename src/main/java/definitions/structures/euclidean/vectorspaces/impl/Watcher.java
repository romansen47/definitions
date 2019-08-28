/**
 * 
 */
package definitions.structures.euclidean.vectorspaces.impl;

import definitions.IWatcher;

/**
 * @author ro
 *
 */
public class Watcher implements IWatcher {

	private static IWatcher instance = null;

	private Watcher() {
	}

	public static IWatcher getInstance() {
		if (instance == null) {
			instance = new Watcher();
		}
		return instance;
	}

	@Override
	public void removeLast() {
		list.remove(list.size() - 1);
	}

}
