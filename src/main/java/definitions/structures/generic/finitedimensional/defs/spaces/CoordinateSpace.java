package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.HilbertSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.FiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public interface CoordinateSpace extends HilbertSpace {

	List<Vector> genericBaseToList() throws Throwable;

	Set<Vector> getGenericBase() throws Throwable;

	int dim() throws Throwable;

	default Map<Vector, Double> getCoordinates(FiniteVector vec) throws Throwable {
		if (contains(vec)) {
			return vec.getCoordinates();
		} else {
			throw new Throwable();
		}
	}

	default Vector get(Map<Vector, Double> tmp) throws Throwable {
		Vector vec = (FiniteVector) nullVec();
		for (final Vector basevec : genericBaseToList()) {
			vec = (FiniteVector) add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	@Override
	default Vector add(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof FiniteVector && vec2 instanceof FiniteVector && vec1.getDim() == vec2.getDim()
				&& vec1.getDim() == dim()) {
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Double> coordinates = new HashMap<>();
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
	default Vector stretch(Vector vec, double r) throws Throwable {
		if (vec instanceof FiniteVector && vec.getDim() == dim()) {
			final Map<Vector, Double> stretched = new HashMap<>();
			final Map<Vector, Double> coordinates = ((FiniteVector) vec).getCoordinates();
			final List<Vector> base = genericBaseToList();
			for (final Vector vec1 : base) {
				stretched.put(vec1, coordinates.get(vec1) * r);
			}
			return new Tuple(stretched);
		}
		return ((VectorSpace) this).stretch(vec, r);
	}

	default FiniteVector normalize(Vector vec) throws Throwable {
		return (FiniteVector) stretch(vec, 1 / norm(vec));
	}

	default Vector get(Vector vec) throws Throwable {
		Map<Vector, Double> map = new HashMap<>();
		int i = 0;
		for (Vector baseVec : ((FiniteDimensionalVectorSpace) this).getBase()) {
			map.put(baseVec, ((FiniteVector)vec).getGenericCoordinates()[i++]);
		}
		return get(map);
	}

	default Vector getBaseVec(Vector vec2) throws Throwable {
		for (Vector vec : getGenericBase()) {
			if (vec2.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	default double getDistance(Vector ans, Vector vec2) throws Throwable {
		Vector diff = (FiniteVector) add(ans, (stretch(vec2, -1)));
		return norm(diff);
	}

}
