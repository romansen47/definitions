package definitions.structures.abstr.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * Linear Mapping: mapping between two vector spaces preserving addition and
 * scalar multiplication.
 *
 * @author ro
 *
 */
public abstract class LinearMapping implements Element, VectorSpaceHomomorphism {

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

	/**
	 * generated serial version uid
	 */
	private static final long serialVersionUID = -2515190501525767017L;

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
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpace getSource() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpace getTarget() {
		return target;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Map<Vector, Scalar>> getLinearity() {
		return linearity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar[][] getGenericMatrix() {
		return genericMatrix;
	}

}
