package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.Ring;

public interface DiscreetRing extends DiscreetGroup, DiscreetSemiRing, Ring {

	@Deprecated
	@Override
	default Element getMinusOne() {
		return this.getInverseElement(this.getOne());
	}

}
