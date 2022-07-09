package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.rings.SemiRing;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * A ring is a group R,such that R without the identity element is a monoid M.
 *
 * Also, the operation-mappings must collaborate in the manner of associativity.
 * That means,
 *
 * for every ring element a, every ring element b and every monoid element c we
 * have
 *
 * c(ab)=(ca)*(cb). this means M.operation(R.operation(a, b), c) =
 * R.operation(M.operation(a,c),M.operation(b,c))
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
