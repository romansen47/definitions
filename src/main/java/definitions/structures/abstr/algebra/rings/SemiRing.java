package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.semigroups.SemiGroup;
import definitions.structures.abstr.vectorspaces.RingElement;

public interface SemiRing extends SemiGroup{
	
	/**
	 * addition in a semi ring is provided by the native operation of the semi ring as monoid.
	 * 
	 * @param first  first element
	 * @param second second element
	 * @return sum of both
	 */
	default RingElement addition(final RingElement first, final RingElement second) {
		return (RingElement) this.operation(first, second);
	}

	/**
	 * Getter for the multiplicative monoid of the ring.
	 * 
	 * @return the multiplicative monoid of the ring
	 */
	Monoid getMuliplicativeMonoid();

	/**
	 * method to multiply elements
	 * 
	 * @param element      first element
	 * @param otherElement second element
	 * @return the multiplication of the elements
	 */
	default RingElement multiplication(final RingElement element, final RingElement otherElement) {
		return (RingElement) this.getMuliplicativeMonoid().operation(element, otherElement);
	}

	/**
	 * method to check whether an element is invertible as an element of the
	 * multiplicative monoid of the ring.
	 * 
	 * @param element the element
	 * @return true if element is a unit
	 */
	boolean isUnit(RingElement element);
	
}
