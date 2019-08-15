package definitions.structures.euclidean.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Endomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

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