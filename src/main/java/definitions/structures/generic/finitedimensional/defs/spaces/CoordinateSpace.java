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

	List<FiniteVector> genericBaseToList() throws Throwable;

	Set<FiniteVector> getGenericBase() throws Throwable;

	int dim() throws Throwable;

	default Map<FiniteVector, Double> getCoordinates(FiniteVector vec) throws Throwable {
		if (contains(vec)) {
			return vec.getCoordinates();
		} else {
			throw new Throwable();
		}
	}

	default Vector get(Map<FiniteVector, Double> tmp) throws Throwable {
		FiniteVector vec = (FiniteVector) nullVec();
		for (final FiniteVector basevec : genericBaseToList()) {
			vec = (FiniteVector) add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	@Override
	default Vector add(Vector vec1, Vector vec2) throws Throwable {
		if (vec1 instanceof FiniteVector && vec2 instanceof FiniteVector && vec1.getDim() == vec2.getDim()
				&& vec1.getDim() == dim()) {
			final List<FiniteVector> base = genericBaseToList();
			final Map<FiniteVector, Double> coordinates = new HashMap<>();
			for (final FiniteVector vec : base) {
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
			final Map<FiniteVector, Double> stretched = new HashMap<>();
			final Map<FiniteVector, Double> coordinates = ((FiniteVector) vec).getCoordinates();
			final List<FiniteVector> base = genericBaseToList();
			for (final FiniteVector vec1 : base) {
				stretched.put(vec1, coordinates.get(vec1) * r);
			}
			return new Tuple(stretched);
		}
		return ((VectorSpace) this).stretch(vec, r);
	}

	default FiniteVector normalize(FiniteVector vec) throws Throwable {
		return (FiniteVector) stretch(vec, 1 / norm(vec));
	}

	default Vector get(FiniteVector vec) throws Throwable {
		Map<FiniteVector, Double> map = new HashMap<>();
		int i = 0;
		for (FiniteVector baseVec : ((FiniteDimensionalVectorSpace) this).getBase()) {
			map.put(baseVec, vec.getGenericCoordinates()[i++]);
		}
		return get(map);
	}

	default FiniteVector getBaseVec(FiniteVector baseVec) throws Throwable {
		for (FiniteVector vec : getGenericBase()) {
			if (baseVec.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	default double getDistance(FiniteVector ans, FiniteVector vec) throws Throwable {
		FiniteVector diff = (FiniteVector) add(ans, (stretch(vec, -1)));
		return norm(diff);
	}

}
