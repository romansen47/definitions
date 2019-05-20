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

	default IFiniteVector get(Map<IFiniteVector, Double> tmp) throws Throwable {
		IFiniteVector vec = (IFiniteVector) nullVec();
		for (final IFiniteVector basevec : genericBaseToList()) {
			vec = (IFiniteVector) add(vec, (IFiniteVector) stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	default IVector add(IFiniteVector vec1, IFiniteVector vec2) throws Throwable {
		if (vec1.getDim() == vec2.getDim() && vec1.getDim() == dim()) {
			final List<IFiniteVector> base = genericBaseToList();
			final Map<IFiniteVector, Double> coordinates = new HashMap<>();
			for (final IFiniteVector vec : base) {
				coordinates.put(getBaseVec(vec), vec1.getCoordinates().get(getBaseVec(vec)) + vec2.getCoordinates().get(getBaseVec(vec)));
			}
			return new FiniteVector(coordinates);
		} else {
			throw new Exception();
		}
	}

	default IVector stretch(IFiniteVector vec, double r) throws Throwable {
		if (vec.getDim() == dim()) {
			final Map<IFiniteVector, Double> stretched = new HashMap<>();
			final Map<IFiniteVector, Double> coordinates = vec.getCoordinates();
			final List<IFiniteVector> base = genericBaseToList();
			for (final IFiniteVector vec1 : base) {
				stretched.put(vec1, coordinates.get(getBaseVec(getBaseVec(vec1))) * r);
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
				return vec;
			}
		}
		return null;
	}

	default double getDistance(IFiniteVector ans, IFiniteVector vec) throws Throwable {
		IFiniteVector diff=(IFiniteVector) add(ans, ((IFiniteVector)stretch(vec,-1)));
		return norm(diff);
	}
//
//	default IFiniteDimensionalVectorSpace getSubSpace(double[][] matrix) throws Throwable {
//		Map<IFiniteVector,Map<IFiniteVector,Double>> coordinates=new HashMap<>();
//		List<IFiniteVector> base=genericBaseToList();
//		for (int i=0;i<matrix[0].length;i++) {
//			Map<IFiniteVector,Double> vecCoordinates=new HashMap<>();
//			for (int j=0;j<matrix.length;j++) {
//				vecCoordinates.put(base.get(j), matrix[i][j]);
//			}
//			coordinates.put(base.get(i), vecCoordinates);
//		}
//		
//	}

}
