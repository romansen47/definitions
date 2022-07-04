package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.algebra.semigroups.Element;

public interface SemiDomain extends SemiRing {

	/**
	 * In a domain the concept of divisibility makes sense.
	 *
	 * @param divisor  the divisor
	 * @param divident the divident
	 * @return true if divisor divides divident
	 */
	boolean divides(Element divisor, Element divident);

	/**
	 * using divisibility we can define reducibility
	 *
	 * @param element the element
	 * @return true if element is irreducible
	 */
	boolean isIrreducible(Element element);

	/**
	 * using divisibility we can define prime elements
	 *
	 * @param element the element
	 * @return true if element is a prime element
	 */
	boolean isPrimeElement(Element element);
}
