package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IHilbertSpace;
import definitions.structures.abstr.IVector;
import definitions.structures.abstr.IVectorSpace;
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

	default IVector get(Map<IFiniteVector, Double> tmp) throws Throwable {
		IFiniteVector vec = (IFiniteVector) nullVec();
		for (final IFiniteVector basevec : genericBaseToList()) {
			vec = (IFiniteVector) add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	@Override
	default IVector add(IVector vec1, IVector vec2) throws Throwable {
		if (vec1 instanceof IFiniteVector && vec2 instanceof IFiniteVector && vec1.getDim() == vec2.getDim()
				&& vec1.getDim() == dim()) {
			final List<IFiniteVector> base = genericBaseToList();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(getBaseVec(vec), ((IFiniteVector) vec1).getCoordinates().get(getBaseVec(vec))
						+ ((IFiniteVector) vec2).getCoordinates().get(getBaseVec(vec)));
			}
			return new FiniteVector(coordinates);
		} else {
			return ((IVectorSpace) this).add(vec1, vec2);
		}
	}

	@Override
	default IVector stretch(IVector vec, double r) throws Throwable {
		if (vec instanceof IFiniteVector && vec.getDim() == dim()) {
			final Map<IFiniteVector, Double> stretched = new HashMap<>();
			final Map<IFiniteVector, Double> coordinates = ((IFiniteVector) vec).getCoordinates();
			final List<IFiniteVector> base = genericBaseToList();
			for (final IFiniteVector vec1 : base) {
				stretched.put(vec1, coordinates.get(vec1) * r);
			}
			return new FiniteVector(stretched);
		}
		return ((IVectorSpace) this).stretch(vec, r);
	}

	default IFiniteVector normalize(IFiniteVector vec) throws Throwable {
		return (IFiniteVector) stretch(vec, 1 / norm(vec));
	}

	default IVector get(IFiniteVector vec) throws Throwable {
		Map<IFiniteVector, Double> map = new HashMap<>();
		int i = 0;
		for (IFiniteVector baseVec : ((FiniteDimensionalVectorSpace) this).getBase()) {
			map.put(baseVec, vec.getGenericCoordinates()[i++]);
		}
		return get(map);
	}

	default IFiniteVector getBaseVec(IFiniteVector baseVec) throws Throwable {
		for (IFiniteVector vec : getGenericBase()) {
			if (baseVec.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	default double getDistance(IFiniteVector ans, IFiniteVector vec) throws Throwable {
		IFiniteVector diff = (IFiniteVector) add(ans, (stretch(vec, -1)));
		return norm(diff);
	}

}
