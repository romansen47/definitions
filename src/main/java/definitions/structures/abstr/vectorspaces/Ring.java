package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.groups.Group;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.Monoid;

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

	default RingElement addition(RingElement element,RingElement otherElement) {
		return (RingElement) operation(element,otherElement);
	};
	
	default RingElement multiplication(RingElement element,RingElement otherElement) {
		return (RingElement) getMuliplicativeMonoid().operation(element,otherElement);
	};
	
	boolean divides(RingElement devisor, RingElement devident);

	/**
	 * Getter for the multiplicative monoid of the ring.
	 * 
	 * @return the multiplicative monoid of the ring
	 */
	Monoid getMuliplicativeMonoid();

	boolean isIrreducible(RingElement element);

	boolean isPrimeElement(RingElement element);

	boolean isUnit(RingElement element);

}
