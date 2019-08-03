package definitions.structures.finitedimensional.real.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.math3.util.Pair;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public final class DerivativeOperator extends FiniteDimensionalLinearMapping implements FiniteDimensionalHomomorphism {

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
		if (cachedDerivatives.get(vec) == null) {
			Map<Integer, Vector> map = new ConcurrentHashMap<>();
			cachedDerivatives.put(vec, map);
		} else {
			Vector cached = cachedDerivatives.get(vec).get(degree);
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
		Scalar[][] tmp = getGenericMatrix();
		Scalar[][] tmpNew = getGenericMatrix();
		for (int i = 1; i < degree; i++) {
			tmpNew = MappingGenerator.getInstance().composition(tmpNew, tmp);
		}
		Vector ans = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping((EuclideanSpace) source, (EuclideanSpace) target, tmpNew).get(vec);
		addToCache(vec, degree, ans);
		return ans;
	}

	private void addToCache(Vector vec, int degree, Vector ans) {

		Map<Integer, Vector> tmp = cachedDerivatives.get(vec);//new ConcurrentHashMap<>();
		tmp.put(degree, ans);
//		cachedDerivatives.put(vec, tmp);
	}

}
