package definitions.structures.euclidean.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.FiniteDimensionalInjection;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * Implementation of finite dimensional embedding.
 * 
 * @author ro
 *
 */
public class InjectiveLinearMapping extends FiniteDimensionalLinearMapping implements FiniteDimensionalInjection {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5351009863618913969L;

	/**
	 * Constructor.
	 * 
	 * @param mapping
	 *            the casted finite dimensional homomorphism.
	 */
	protected InjectiveLinearMapping(final FiniteDimensionalHomomorphism mapping) {
		super((EuclideanSpace) mapping.getSource(), (EuclideanSpace) mapping.getTarget(), mapping.getLinearity());
	}

	/**
	 * Constructor.
	 * 
	 * @param source
	 *            the source vector space.
	 * @param target
	 *            the target vector space.
	 * @param coordinates
	 *            the coordinates mapping.
	 */
	public InjectiveLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinates) {
		super(source, target, coordinates);
	}

}
