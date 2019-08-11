package definitions.structures.finitedimensional.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalEmbedding;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * Implementation of finite dimensional embedding.
 * 
 * @author ro
 *
 */
public class InjectiveLinearMapping extends FiniteDimensionalLinearMapping implements FiniteDimensionalEmbedding {

	/**
	 * Constructor.
	 * 
	 * @param mapping the casted finite dimensional homomorphism.
	 */
	protected InjectiveLinearMapping(final FiniteDimensionalHomomorphism mapping) {
		super((EuclideanSpace) mapping.getSource(), (EuclideanSpace) mapping.getTarget(), mapping.getLinearity());
	}

	/**
	 * Constructor.
	 * 
	 * @param source      the source vector space.
	 * @param target      the target vector space.
	 * @param coordinates the coordinates mapping.
	 */
	public InjectiveLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinates) {
		super(source, target, coordinates);
	}

}
