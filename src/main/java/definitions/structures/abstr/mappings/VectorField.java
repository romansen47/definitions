/**
 * 
 */
package definitions.structures.abstr.mappings;

import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public interface VectorField extends Mapping {

	@Override
	default EuclideanSpace getTarget() {
		return (EuclideanSpace) getSource();
	}

}
