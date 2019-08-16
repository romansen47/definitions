package definitions.structures.abstr.vectorspaces;

import definitions.IWatcher;
import definitions.structures.euclidean.vectorspaces.impl.Watcher;

public interface TechnicalProvider {

	final static IWatcher builder = Watcher.getInstance();

	static IWatcher getStringBuilder() {
		return builder;
	};
	
	
	
}
