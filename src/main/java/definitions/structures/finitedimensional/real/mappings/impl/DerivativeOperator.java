package definitions.structures.finitedimensional.real.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public final class DerivativeOperator extends FiniteDimensionalLinearMapping implements Homomorphism{

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
			Map<Integer, Vector> map = new ConcurrentHashMap<>();
			this.cachedDerivatives.put(vec, map);
		} else {
			Vector cached = this.cachedDerivatives.get(vec).get(degree);
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
		Scalar[][] tmp = this.getGenericMatrix();
		Scalar[][] tmpNew = this.getGenericMatrix();
		for (int i = 1; i < degree; i++) {
			tmpNew = MappingGenerator.getInstance().composition(tmpNew, tmp);
		}
		Vector ans = super.get(vec);
		this.addToCache(vec, degree, ans);
		return ans;
	}

	private void addToCache(Vector vec, int degree, Vector ans) {
		Map<Integer, Vector> tmp = this.cachedDerivatives.get(vec);// new ConcurrentHashMap<>();
		tmp.put(degree, ans);
//		cachedDerivatives.put(vec, tmp);
	}

}
