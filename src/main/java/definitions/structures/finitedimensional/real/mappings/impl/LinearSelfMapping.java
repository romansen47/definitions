package definitions.structures.finitedimensional.real.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Endomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

/**
 * Linear self mapping.
 * 
 * @author ro
 *
 */
public class LinearSelfMapping extends FiniteDimensionalLinearMapping implements Endomorphism {

	/**
	 * Constructor.
	 * 
	 * @param source      the source and target space.
	 * @param coordinates the coordinates.
	 */
	protected LinearSelfMapping(final EuclideanSpace source, final Map<Vector, Map<Vector, Scalar>> coordinates) {
		super(source, source, coordinates);
	}

}
