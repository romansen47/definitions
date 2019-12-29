package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * 
 * @author ro
 *
 *         A ring is a group R,such that r without the identity element is a
 *         monoid M.
 * 
 *         Moveover, the operation-mappings must collaborate in the manner of
 *         associativity. That means,
 * 
 *         for every ring element a, every ring element b and every monoid
 *         element c we have
 * 
 *         M.operation(R.operation(a, b), c) =
 *         R.operation(M.operation(a,c),M.operation(b,c))
 *
 */
public interface Ring extends Group {

	RingElement getOne();
	
	default RingElement addition(final RingElement element, final RingElement otherElement) {
		return (RingElement) this.operation(element, otherElement);
	}

	boolean divides(RingElement devisor, RingElement devident);

	/**
	 * Getter for the multiplicative monoid of the ring.
	 * 
	 * @return the multiplicative monoid of the ring
	 */
	Monoid getMuliplicativeMonoid();

	boolean isUnit(RingElement element);

	default RingElement multiplication(final RingElement element, final RingElement otherElement) {
		return (RingElement) this.getMuliplicativeMonoid().operation(element, otherElement);
	}

}
