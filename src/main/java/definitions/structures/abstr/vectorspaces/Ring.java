package definitions.structures.abstr.vectorspaces;

import definitions.structures.abstr.algebra.groups.Group;
import definitions.structures.abstr.algebra.monoids.Monoid;

/**
 * 
 * @author ro
 *
 *         A ring is a group R,such that R without the identity element is a
 *         monoid M.
 * 
 *         Also, the operation-mappings must collaborate in the manner of
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

	/**
	 * addition in a ring is provided by the native operation of the ring as monoid.
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
	 * we define rings with a multiplicative identity.
	 * 
	 * @return the multiplicative identity
	 */
	RingElement getOne();

	/**
	 * method to check whether an element is invertible as an element of the
	 * multiplicative monoid of the ring.
	 * 
	 * @param element the element
	 * @return true if element is a unit
	 */
	boolean isUnit(RingElement element);

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

}
