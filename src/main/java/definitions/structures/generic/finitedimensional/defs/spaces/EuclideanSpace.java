package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.HilbertSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public interface EuclideanSpace extends HilbertSpace {

	List<Vector> genericBaseToList() throws Throwable;

	Set<Vector> getGenericBase() throws Throwable;

	int dim() throws Throwable;

	default Vector get(final Map<Vector, Double> tmp) throws Throwable {
		Vector vec = nullVec();
		for (final Vector basevec : genericBaseToList()) {
			vec = add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	@Override
	default Vector add(final Vector vec1, final Vector vec2) throws Throwable {
		if ((vec1 instanceof FiniteVector) && (vec2 instanceof FiniteVector) && (vec1.getDim() == vec2.getDim())
				&& (vec1.getDim() == dim())) {
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
			for (final Vector vec : base) {
				coordinates.put(getBaseVec(vec), ((FiniteVector) vec1).getCoordinates().get(getBaseVec(vec))
						+ ((FiniteVector) vec2).getCoordinates().get(getBaseVec(vec)));
			}
			return new Tuple(coordinates);
		} else {
			return ((VectorSpace) this).add(vec1, vec2);
		}
	}

	@Override
	default Vector stretch(final Vector vec, final double r) throws Throwable {
		if ((vec instanceof FiniteVector) && (vec.getDim() == dim())) {
			final Map<Vector, Double> stretched = new ConcurrentHashMap<>();
			final Map<Vector, Double> coordinates = ((FiniteVector) vec).getCoordinates();
			final List<Vector> base = genericBaseToList();
			for (final Vector vec1 : base) {
				stretched.put(vec1, coordinates.get(vec1) * r);
			}
			return new Tuple(stretched);
		}
		return ((VectorSpace) this).stretch(vec, r);
	}

	default Vector normalize(final Vector vec) throws Throwable {
		return stretch(vec, 1 / norm(vec));
	}

	default Vector get(final Vector vec) throws Throwable {
		final Map<Vector, Double> map = new ConcurrentHashMap<>();
		int i = 0;
		for (final Vector baseVec : ((FiniteDimensionalVectorSpace) this).getBase()) {
			map.put(baseVec, ((FiniteVector) vec).getGenericCoordinates()[i++]);
		}
		return get(map);
	}

	default Vector getBaseVec(final Vector vec2) throws Throwable {
		for (final Vector vec : genericBaseToList()) {
			if (vec2.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	Vector getCoordinates(Vector vec) throws Throwable;

	default double getDistance(final Vector ans, final Vector vec2) throws Throwable {
		final Vector diff = add(ans, (stretch(vec2, -1)));
		return norm(diff);
	}

	List<Vector> getOrthonormalBase(List<Vector> base) throws Throwable;

	Vector projection(Vector w, Vector v) throws Throwable;

}
