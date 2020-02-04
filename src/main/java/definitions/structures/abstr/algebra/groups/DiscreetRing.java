package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.Ring;

public interface DiscreetRing extends DiscreetGroup, DiscreetSemiRing, Ring {

	@Override
	default Element getMinusOne() {
		return get(-1.0);
	}
	
}
