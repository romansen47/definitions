package definitions.structures.euclidean.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Automorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * Implementation of injective linear self mapping.
 * 
 * @author ro
 *
 */
public class InvertibleSelfMapping extends InjectiveLinearMapping implements Automorphism {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8391047212917259227L;

	/**
	 * Constructor.
	 * 
	 * @param source      the source and target space.
	 * @param coordinates the coordinates.
	 */
	protected InvertibleSelfMapping(final EuclideanSpace source, final Map<Vector, Map<Vector, Scalar>> coordinates) {
		super(source, source, coordinates);
	}

}
