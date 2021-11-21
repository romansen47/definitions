/**
 *
 */
package definitions.structures.abstr.mappings;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author ro
 *
 */
public interface Functional extends VectorSpaceHomomorphism {

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Vector get(final Element vec) {
		return ((EuclideanSpace) getSource()).innerProduct(getSourceVec(), (Vector) vec);
	}

	Vector getSourceVec();

	/**
	 * {@inheritDoc}
	 */
	@Override
	default EuclideanSpace getTarget() {
		return ((EuclideanSpace) getSource()).getField();
	}

}
