package definitions.structures.abstr.impl;

import java.util.Map;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;

/**
 * Linear Mapping: mapping between two vector spaces preserving addition and
 * scalar multiplication.
 * 
 * @author ro
 *
 */
public abstract class LinearMapping implements Homomorphism {

	/**
	 * The source vector space.
	 */
	protected final VectorSpace source;

	/**
	 * The target vector space.
	 */
	protected final VectorSpace target;

	/**
	 * The restriction to the base.
	 */
	protected Map<Vector, Map<Vector, Scalar>> linearity;

	/**
	 * Linearity as a matrix.
	 */
	protected Scalar[][] genericMatrix;

	/**
	 * Constructor. Called by instance of MappingGenerator.
	 * 
	 * @param source the source vector space.
	 * @param target the target vector space.
	 */
	protected LinearMapping(final VectorSpace source, final VectorSpace target) {
		this.source = source;
		this.target = target;
	}

}
