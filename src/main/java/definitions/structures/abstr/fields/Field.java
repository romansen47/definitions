package definitions.structures.abstr.fields;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.EuclideanAlgebra;
import definitions.structures.abstr.vectorspaces.LinearMappingsSpace;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface Field extends EuclideanAlgebra, FieldMethods {

	default Vector inverse(Vector factor) {
		final VectorSpace multLinMaps = new LinearMappingsSpace(this, this);
		FiniteDimensionalHomomorphism hom = new FiniteDimensionalLinearMapping(this, this) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -4878554588629268392L;

			@Override
			public Vector get(Vector vec) {
				return getTarget().nullVec();
			}

			@Override
			public Map<Vector, Map<Vector, Scalar>> getLinearity() {
				final Map<Vector, Map<Vector, Scalar>> coord = new HashMap<>();
				for (final Vector vec : ((EuclideanSpace) getSource()).genericBaseToList()) {
					coord.put(vec, target.nullVec().getCoordinates());
				}
				return coord;
			}

			@Override
			public Scalar[][] getGenericMatrix() {
				final Scalar[][] mat = new Scalar[target.getDim()][source.getDim()];
				for (final Scalar[] entry : mat) {
					for (Scalar scalar : entry) {
						scalar = RealLine.getInstance().getZero();
					}
				}
				return mat;
			}
		};
		for (final Vector vec : genericBaseToList()) {
			final Vector tmp = this.getMultiplicationMatrix().get(vec);
			hom = (FiniteDimensionalHomomorphism) multLinMaps.add(hom,
					multLinMaps.stretch(tmp, factor.getCoordinates().get(vec)));
		}
		return hom.solve((FiniteVector) getOne());
	}

	@Override
	Vector getOne();

	default Vector getZero() {
		return nullVec();
	}

	Scalar conjugate(Scalar value);

}
