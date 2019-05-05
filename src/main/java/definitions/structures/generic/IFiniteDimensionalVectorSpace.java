package definitions.structures.generic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IHilbertSpace;
import definitions.structures.abstr.IVec;

public interface IFiniteDimensionalVectorSpace extends IHilbertSpace {

	List<RealVec> getGenericBase();

	Set<IVec> baseToArray();

	int dim();

	default Map<IVec, Double> getCoordinates(IVec vec) throws Throwable {
		if (contains(vec)) {
			return ((RealVec) vec).getCoordinates();
		} else {
			throw new Throwable();
		}
	}

	default IVec get(Map<IVec, Double> coordinates) throws Throwable {
		IVec vec = nullVec();
		for (final IVec basevec : getGenericBase()) {
			vec = add(vec, stretch(basevec, coordinates.get(basevec).doubleValue()));
		}
		return vec;
	}

	default IVec add(IVec vec1, IVec vec2) throws Throwable {
		if (vec1 instanceof RealVec && vec2 instanceof RealVec
				&& ((RealVec) vec1).getDim() == (((RealVec) vec2).getDim()) && ((RealVec) vec1).getDim() == dim()) {
			final List<RealVec> base = getGenericBase();
			final Map<IVec, Double> coordinates = new HashMap<>();
			for (final IVec vec : base) {
				coordinates.put(vec, ((RealVec) vec1).getGenericCoordinates().get(vec)
						+ ((RealVec) vec2).getGenericCoordinates().get(vec));
			}
			return new RealVec(coordinates);
		} else {
			throw new Exception();
		}
	}

	default IVec stretch(IVec vec, double r) throws Throwable {
		if (vec instanceof RealVec && ((RealVec) vec).getDim() == dim()) {
			final RealVec Vec = (RealVec) vec;
			final Map<IVec, Double> stretched = Vec.getGenericCoordinates();
			final List<RealVec> base = getGenericBase();
			for (final IVec vec1 : base) {
				stretched.put(vec1, stretched.get(vec1) * r);
			}
			return new RealVec(stretched);
		}
		throw new Throwable();
	}

	default IVec normalize(IVec vec) throws Throwable {
		return stretch(vec, 1 / norm(vec));
	}
}
