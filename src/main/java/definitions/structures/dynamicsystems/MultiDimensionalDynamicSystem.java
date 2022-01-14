/**
 *
 */
package definitions.structures.dynamicsystems;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public interface MultiDimensionalDynamicSystem extends DynamicSystem {

	/**
	 * the phase space of the dynamic system.
	 *
	 * @return the phase space
	 */
	@Override
	EuclideanSpace getPhaseSpace();

}
