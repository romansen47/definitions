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

	GroupElement get(Integer index);

}
