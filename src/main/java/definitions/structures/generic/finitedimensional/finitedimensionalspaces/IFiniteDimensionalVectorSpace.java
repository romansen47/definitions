package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IHilbertSpace;
import definitions.structures.abstr.IVector;

public interface IFiniteDimensionalVectorSpace extends IHilbertSpace {

	List<FiniteVector> getGenericBase();

	Set<IVector> baseToArray();

	int dim();

	default Map<IFiniteVector, Double> getCoordinates(IFiniteVector vec) throws Throwable {
		if (contains(vec)) {
			return vec.getCoordinates();
		} else {
			throw new Throwable();
		}
	}

	default IVector get(Map<IVector, Double> coordinates) throws Throwable {
		IFiniteVector vec = (IFiniteVector) nullVec();
		for (final IFiniteVector basevec : getGenericBase()) {
			vec = (IFiniteVector) add(vec, (IFiniteVector) stretch(basevec, coordinates.get(basevec).doubleValue()));
		}
		return vec;
	}

	default IVector add(IFiniteVector vec1, IFiniteVector vec2) throws Throwable {
		if ( vec1.getDim() == vec2.getDim() && vec1.getDim() == dim()) {
			final List<FiniteVector> base = getGenericBase();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(vec,vec1.getGenericCoordinates().get(vec)
						+ vec2.getGenericCoordinates().get(vec));
			}
			return new FiniteVector(coordinates);
		} else {
			throw new Exception();
		}
	}

	default IVector stretch(IFiniteVector vec, double r) throws Throwable {
		if (vec.getDim() == dim()) {
			final FiniteVector Vec = (FiniteVector) vec;
			final Map<IFiniteVector, Double> stretched = Vec.getGenericCoordinates();
			final List<FiniteVector> base = getGenericBase();
			for (final IFiniteVector vec1 : base) {
				stretched.put(vec1, stretched.get(vec1) * r);
			}
			return new FiniteVector(stretched);
		}
		throw new Throwable();
	}

	default IFiniteVector normalize(IFiniteVector vec) throws Throwable {
		return (IFiniteVector) stretch(vec, 1 / norm(vec));
	}
}
