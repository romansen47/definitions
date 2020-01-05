/**
 * 
 */
package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;

/**
 * @author RoManski
 *
 */
public interface DiscreetGroup extends DiscreetMonoid, Group {
	
	/**
	 * Getter for the identity element
	 * 
	 * @return the neutral element of the semi group
	 */
	DiscreetGroupElement getNeutralElement();
	
	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 * 
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	DiscreetGroupElement get(Integer representant);
}
