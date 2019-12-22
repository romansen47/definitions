package definitions.structures.abstr.vectorspaces;

import java.io.Serializable;

import definitions.IWatcher;
import definitions.structures.euclidean.vectorspaces.impl.Watcher;

public interface TechnicalProvider extends Serializable {

	IWatcher builder = Watcher.getInstance();

	static IWatcher getStringBuilder() {
		return builder;
	}

}
