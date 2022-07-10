package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * A ring is a group R,such that R without the identity element is a monoid M.
 *
 * @author ro
 */
public interface Ring extends Group, SemiRing {

	/**
	 * A ring is an additive group, therefore every element in the ring is
	 * invertible wrt to addition. In detail we have
	 * 
	 * (-x)=(-1)x, so we actually only need the additive inverse of one.
	 * 
	 * @return the additive inverse of one
	 */
	Element getMinusOne();

}
