package definitions.structures.euclidean.mappings.impl;

import java.util.Map;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.FiniteDimensionalAutomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class FiniteDimensionalDerivativeOperator extends DerivativeOperator implements FiniteDimensionalAutomorphism {

	private static final long serialVersionUID = -6105857651030763798L;

	public FiniteDimensionalDerivativeOperator(final EuclideanSpace source, final EuclideanSpace target) {
		super(source, target);
	}

	public FiniteDimensionalDerivativeOperator(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinatesMap) {
		super(source, target, coordinatesMap);
	}

}
