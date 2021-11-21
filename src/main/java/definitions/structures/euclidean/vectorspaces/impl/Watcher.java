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

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private static IWatcher instance = null;

	public static IWatcher getInstance() {
		if (instance == null) {
			instance = new Watcher();
		}
		return instance;
	}

	private Watcher() {
	}

	@Override
	public void removeLast() {
		list.remove(list.size() - 1);
	}

}
