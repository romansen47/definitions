package definitions.structures.euclidean.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.aop.lib.Wrap;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

@Wrap
public final class DerivativeOperator extends FiniteDimensionalLinearMapping implements Homomorphism {

	private static final long serialVersionUID = -5103583236895270490L;

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

	@Wrap
	public Vector get(Vector vec, int degree) {
//		if (this.cachedDerivatives.get(vec) == null) {
//			final Map<Integer, Vector> map = new ConcurrentHashMap<>();
//			this.cachedDerivatives.put(vec, map);
//		} else {
//			final Vector cached = this.cachedDerivatives.get(vec).get(degree);
//			if (cached != null) {
//				System.out.println("hit!");
//				return cached;
//			}
//		}
		if (degree < 0) {
			return null;
		}
		if (degree == 0) {
			return vec;
		}

		if (degree == 1) {
			if (((FiniteVectorMethods) vec).getCoordinates() == null) {
				return (this.get(vec));// .getProjection((EuclideanSpace) getSource());
			}
			return ((Function) this.get(vec)).getProjection((EuclideanSpace) this.getSource());
		}
		if (vec instanceof FunctionTuple) {
			return this.get(this.get(vec), degree - 1);
		}
//		Scalar[][] tmp = this.getGenericMatrix().clone();
		Vector test = ((Function) vec).getProjection((EuclideanSpace) this.getSource());
		for (int i = 0; i < degree; i++) {
			test = this.get(test);
//			tmp = MappingGenerator.getInstance().composition(this.getGenericMatrix().clone(), tmp);
		}
//		final Vector ans = ((Function) MappingGenerator.getInstance()
//				.getFiniteDimensionalLinearMapping((EuclideanSpace) this.source, (EuclideanSpace) this.target, tmp)
//				.get(vec)).getProjection((EuclideanSpace) this.getSource());
//		this.addToCache(vec, degree, ans);
		return test;// ans;
	}

	private void addToCache(Vector vec, int degree, Vector ans) {
		final Map<Integer, Vector> tmp = this.cachedDerivatives.get(vec);// new ConcurrentHashMap<>();
		tmp.put(degree, ans);
		// cachedDerivatives.put(vec, tmp);
	}

}
