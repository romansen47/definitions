package definitions.structures.euclidean.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public final class DerivativeOperator extends FiniteDimensionalLinearMapping implements Homomorphism {

	public DerivativeOperator(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinates) {
		super(source, target, coordinates);
	}

	public DerivativeOperator(EuclideanSpace source, EuclideanSpace target) {
		super(source, target);
		this.linearity = new HashMap<>();
		for (final Vector baseVec : source.genericBaseToList()) {
			final Function derivative = ((Function) baseVec).getDerivative();
			final Map<Vector, Scalar> derivativeOnSpace = derivative.getCoordinates(target);
			this.linearity.put(baseVec, derivativeOnSpace);
		}
		this.getGenericMatrix();
	}

	final private Map<Vector, Map<Integer, Vector>> cachedDerivatives = new ConcurrentHashMap<>();

	public Vector get(Vector vec, int degree) {
		if (this.cachedDerivatives.get(vec) == null) {
			final Map<Integer, Vector> map = new ConcurrentHashMap<>();
			this.cachedDerivatives.put(vec, map);
		} else {
			final Vector cached = this.cachedDerivatives.get(vec).get(degree);
			if (cached != null) {
				System.out.println("hit!");
				return cached;
			}
		}
		if (degree < 0) {
			return null;
		}
		if (degree == 0) {
			return vec;
		}

		if (degree == 1) {
			if (vec instanceof FunctionTuple) {
				return (this.get(vec));// .getProjection((EuclideanSpace) getSource());
			}
			return ((Function) this.get(vec)).getProjection((EuclideanSpace) this.getSource());
		}
		if (vec instanceof FunctionTuple) {
			return this.get(vec, degree - 1);
		}
		Scalar[][] tmp = this.getGenericMatrix().clone();
		for (int i = 1; i < degree; i++) {
			tmp = MappingGenerator.getInstance().composition(this.getGenericMatrix().clone(), tmp);
		}
		final Vector ans = ((Function) MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping((EuclideanSpace) this.source, (EuclideanSpace) this.target, tmp)
				.get(vec)).getProjection((EuclideanSpace) this.getSource());
		this.addToCache(vec, degree, ans);
		return ans;
	}

	private void addToCache(Vector vec, int degree, Vector ans) {
		final Map<Integer, Vector> tmp = this.cachedDerivatives.get(vec);// new ConcurrentHashMap<>();
		tmp.put(degree, ans);
//		cachedDerivatives.put(vec, tmp);
	}

}