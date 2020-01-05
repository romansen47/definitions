package definitions.structures.abstr.algebra.rings;

import definitions.structures.abstr.vectorspaces.Ring;
import definitions.structures.abstr.vectorspaces.RingElement;

/**
 * 
 * @author ro
 *
 *         Domain is a (possibly non-commutative) ring, where
 *
 *         a*x = b*x for non-zero x always implies a=b. This means that the
 *         mapping
 * 
 *         a |- a*x is injective for every non-zero x.
 *
 */
public interface Domain extends Ring {

	/**
	 * In a domain the concept of divisibility makes sense.
	 * 
	 * @param divisor the divisor
	 * @param divident the divident
	 * @return true if divisor divides divident
	 */
	boolean divides(RingElement divisor, RingElement divident);

	/**
	 * using divisibility we can define reducibility
	 * 
	 * @param element the element
	 * @return true if element is irreducible
	 */
	boolean isIrreducible(RingElement element);

	/**
	 * using divisibility we can define prime elements
	 * 
	 * @param element the element
	 * @return true if element is a prime element
	 */
	boolean isPrimeElement(RingElement element);

}
