/**
 * 
 */
package definitions.structures.abstr.groups;

import definitions.structures.abstr.groups.impl.DiscreetMonoid;

/**
 * @author RoManski
 *
 */
public interface DiscreetGroup extends DiscreetMonoid, Group {

	GroupElement get(Integer index);

}
