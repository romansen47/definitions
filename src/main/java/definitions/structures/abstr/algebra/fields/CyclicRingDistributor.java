package definitions.structures.abstr.algebra.fields;

import java.util.HashMap;
import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.groups.impl.Int;
import definitions.structures.abstr.vectorspaces.Ring;

public interface CyclicRingDistributor extends XmlPrintable {

	Map<Integer, Ring> cachedRings = new HashMap<>();

	static Map<Integer, Ring> getCachedRings() {
		return cachedRings;
	}

	static Ring getCyclicRing(final Int i) {
		final Ring ans = cachedRings.get(i.getRepresentant());
		if (ans != null) {
			return ans;
		}
		if (i.isPrime()) {
			return null;
		}
		return null;
	}
}
