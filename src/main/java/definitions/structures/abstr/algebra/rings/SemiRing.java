package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.SemiGroup;

public interface SemiRing extends Monoid{
	
	/**
	 * addition in a semi ring is provided by the native operation of the semi ring as monoid.
	 * 
	 * @param first  first element
	 * @param second second element
	 * @return sum of both
	 */
	default Element addition(final Element first, final Element second) {
		return (Element) this.operation(first, second);
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
	default Element multiplication(final Element element, final Element otherElement) {
		return (Element) this.getMuliplicativeMonoid().operation(element, otherElement);
	}

	/**
	 * method to check whether an element is invertible as an element of the
	 * multiplicative monoid of the ring.
	 * 
	 * @param element the element
	 * @return true if element is a unit
	 */
	boolean isUnit(Element element);

	/**
	 * we define rings with a multiplicative identity.
	 * 
	 * @return the multiplicative identity
	 */
	Element getOne();
}
