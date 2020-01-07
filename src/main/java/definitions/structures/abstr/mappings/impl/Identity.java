package definitions.structures.abstr.mappings.impl;

import definitions.structures.abstr.algebra.monoids.Monoid;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.Endomorphism;
import definitions.structures.abstr.mappings.VectorField;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public abstract class Identity extends LinearMapping implements Endomorphism, VectorField {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6209038203547448760L;

	public Identity(final VectorSpace source) {
		super(source, source);
	}

	@Override
	public Vector get(final Element vec) {
		return (Vector) vec;
	}

	@Override
	public Monoid getTarget() {
		return VectorField.super.getTarget();
	}

}
