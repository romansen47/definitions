package definitions.structures.abstr.fields;

import java.util.HashMap;
import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.groups.impl.Integers.Int;
import definitions.structures.abstr.vectorspaces.Ring;

public interface CyclicRingDistributor extends XmlPrintable {

	static Map<Integer, Ring> cachedRings = new HashMap<>();

	static Map<Integer, Ring> getCachedRings() {
		return cachedRings;
	}

	static Ring getCyclicRing(Int i) {
		final Ring ans = cachedRings.get(i.getValue());
		if (ans != null) {
			return ans;
		}
		if (i.isPrime()) {
			return null;
		}
		return null;
	}
}
