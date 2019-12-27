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

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector get(final Vector vec) {
		return ((EuclideanSpace) this.getSource()).innerProduct(this.getSourceVec(), vec);
	}

	Vector getSourceVec();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default EuclideanSpace getTarget() {
		return ((EuclideanSpace)this.getSource()).getField();
	}

}
