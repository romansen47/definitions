/**
 * 
 */
package definitions.structures.abstr.mappings;

import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public interface Functional extends Homomorphism {

	Vector getSourceVec();

	@Override
	default EuclideanSpace getTarget() {
		return getSource().getField();
	}

	@Override
	public default Vector get(Vector vec) {
		return ((EuclideanSpace) getSource()).innerProduct(getSourceVec(), vec);
	}

}
