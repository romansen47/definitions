package definitions.structures.finitedimensional.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.mappings.Automorphism;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * Implementation of injective linear self mapping.
 * 
 * @author ro
 *
 */
public class InvertibleSelfMapping extends InjectiveLinearMapping implements Automorphism {

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
