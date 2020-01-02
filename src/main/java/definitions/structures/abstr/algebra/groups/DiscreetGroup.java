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
	 * {@inheritDoc}
	 */
	@Override
	GroupElement get(Integer index);

}
