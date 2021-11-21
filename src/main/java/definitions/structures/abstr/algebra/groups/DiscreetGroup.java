/**
 *
 */
package definitions.structures.abstr.algebra.groups;

import definitions.structures.abstr.algebra.monoids.DiscreetMonoid;
import definitions.structures.abstr.algebra.semigroups.Element;

/**
 * @author RoManski
 *
 */
public interface DiscreetGroup extends DiscreetMonoid, Group {

	/**
	 * In a discreet monoid we have countably many elements and can define a getter
	 * for integers.
	 *
	 * @param representant the representant of the element.
	 * @return the corresponding monoid element.
	 */
	@Override
	Element get(Double representant);
}
