package definitions.structures.abstr.mappings.impl;

import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorField;
import definitions.structures.abstr.mappings.VectorSpaceAutomorphism;
import definitions.structures.abstr.mappings.VectorSpaceIsomorphism;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public abstract class Identity extends LinearMapping implements VectorSpaceAutomorphism, VectorField {

	private static final long serialVersionUID = 1L;

	protected Identity(final VectorSpace source) {
		super(source, source);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpaceIsomorphism getInverse() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector get(final Element vec) {
		return (Vector) vec;
	}

}
