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

	default Map<IVector, Double> getCoordinates(IVector vec) throws Throwable {
		if (contains(vec)) {
			return ((FiniteVector) vec).getCoordinates();
		} else {
			throw new Throwable();
		}
	}

	default IVector get(Map<IVector, Double> coordinates) throws Throwable {
		IVector vec = nullVec();
		for (final IVector basevec : getGenericBase()) {
			vec = add(vec, stretch(basevec, coordinates.get(basevec).doubleValue()));
		}
		return vec;
	}

	default IVector add(IVector vec1, IVector vec2) throws Throwable {
		if (vec1 instanceof FiniteVector && vec2 instanceof FiniteVector
				&& ((FiniteVector) vec1).getDim() == (((FiniteVector) vec2).getDim()) && ((FiniteVector) vec1).getDim() == dim()) {
			final List<FiniteVector> base = getGenericBase();
			final Map<IVector, Double> coordinates = new HashMap<>();
			for (final IVector vec : base) {
				coordinates.put(vec, ((FiniteVector) vec1).getGenericCoordinates().get(vec)
						+ ((FiniteVector) vec2).getGenericCoordinates().get(vec));
			}
			return new FiniteVector(coordinates);
		} else {
			throw new Exception();
		}
	}

	default IVector stretch(IVector vec, double r) throws Throwable {
		if (vec instanceof FiniteVector && ((FiniteVector) vec).getDim() == dim()) {
			final FiniteVector Vec = (FiniteVector) vec;
			final Map<IVector, Double> stretched = Vec.getGenericCoordinates();
			final List<FiniteVector> base = getGenericBase();
			for (final IVector vec1 : base) {
				stretched.put(vec1, stretched.get(vec1) * r);
			}
			return new FiniteVector(stretched);
		}
		throw new Throwable();
	}

	default IVector normalize(IVector vec) throws Throwable {
		return stretch(vec, 1 / norm(vec));
	}
}
