package definitions.structures.abstr.algebra.monoids;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.algebra.semigroups.DiscreetSemiGroup;
import definitions.structures.abstr.algebra.semigroups.Element;

public interface DiscreetMonoid extends Monoid, DiscreetSemiGroup {
	/**
	 * Getter for the identity element
	 * 
	 * @return the neutral element of the semi group
	 */
	@Override
	Element getNeutralElement();
	
	/**
	 * the operation on the monoid.
	 * 
	 * @param first  first monoid element
	 * @param second second monoid element
	 * @return product of both of them
	 */
	@Override
	Element operation(Element first, Element second);
	

	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 * 
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	@Override
	Element get(Integer representant);
}
