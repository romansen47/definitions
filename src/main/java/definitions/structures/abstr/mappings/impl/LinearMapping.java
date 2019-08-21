package definitions.structures.abstr.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

/**
 * Linear Mapping: mapping between two vector spaces preserving addition and
 * scalar multiplication.
 * 
 * @author ro
 *
 */
public abstract class LinearMapping implements Homomorphism {

	/**
	 * 
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
	 * Constructor. Called by instance of MappingGenerator.
	 * 
	 * @param source
	 *            the source vector space.
	 * @param target
	 *            the target vector space.
	 */
	protected LinearMapping(final VectorSpace source, final VectorSpace target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public VectorSpace getSource() {
		return this.source;
	}

	@Override
	public VectorSpace getTarget() {
		return this.target;
	}

	// @Override
	// public Map<Vector,Map<Vector,Scalar>> getCoordinates(){
	// Map<Vector,Map<Vector,Scalar>> coord=new HashMap<>();
	// for (Vector vec1:source.genericBaseToList()) {
	//
	// }
	// }
}
