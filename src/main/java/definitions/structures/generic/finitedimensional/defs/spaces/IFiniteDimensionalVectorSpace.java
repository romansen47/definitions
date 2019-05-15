package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IHilbertSpace;
import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public interface IFiniteDimensionalVectorSpace extends IHilbertSpace {

	List<IFiniteVector> genericBaseToList() throws Throwable;

	Set<IFiniteVector> getGenericBase() throws Throwable;

	int dim() throws Throwable;

	default Map<IFiniteVector, Double> getCoordinates(IFiniteVector vec) throws Throwable {
		if (contains(vec)) {
			return vec.getCoordinates();
		} else {
			throw new Throwable();
		}
	}

	default IVector get(Map<IVector, Double> coordinates) throws Throwable {
		IFiniteVector vec = (IFiniteVector) nullVec();
		for (final IFiniteVector basevec : genericBaseToList()) {
			vec = (IFiniteVector) add(vec, (IFiniteVector) stretch(basevec, coordinates.get(basevec).doubleValue()));
		}
		return vec;
	}

	default IVector add(IFiniteVector vec1, IFiniteVector vec2) throws Throwable {
		if (vec1.getDim() == vec2.getDim() && vec1.getDim() == dim()) {
			final List<IFiniteVector> base = genericBaseToList();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(vec, vec1.getCoordinates().get(vec) + vec2.getCoordinates().get(vec));
			}
			return new FiniteVector(coordinates);
		} else {
			throw new Exception();
		}
	}

	default IVector stretch(IFiniteVector vec, double r) throws Throwable {
		if (vec.getDim() == dim()) {
			final Map<IFiniteVector, Double> stretched = vec.getCoordinates();
			final List<IFiniteVector> base = genericBaseToList();
			for (final IFiniteVector vec1 : base) {
				stretched.put(vec1, stretched.get(getBaseVec(vec1)) * r);
			}
			return new FiniteVector(stretched);
		}
		throw new Throwable();
	}

	default IFiniteVector normalize(IFiniteVector vec) throws Throwable {
		return (IFiniteVector) stretch(vec, 1 / norm(vec));
	}

	default IFiniteVector getBaseVec(IFiniteVector baseVec) throws Throwable {
		for (IFiniteVector vec : getGenericBase()) {
			if (baseVec.equals(vec)) {
				return baseVec;
			}
		}
		return null;
	}
}
