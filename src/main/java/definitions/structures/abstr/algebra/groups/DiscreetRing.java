package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.rings.DiscreetSemiRing;
import definitions.structures.abstr.algebra.rings.Ring;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * A discreet ring is a ring that is finite or countably infinite.
 *
 * @author ro
 */
public interface DiscreetRing extends DiscreetGroup, DiscreetSemiRing, Ring {

	@Override
	default Element getMinusOne() {
		return this.getInverseElement(this.getOne());
	}

}
