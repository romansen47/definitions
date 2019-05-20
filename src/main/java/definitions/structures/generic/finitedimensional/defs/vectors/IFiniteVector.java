package definitions.structures.generic.finitedimensional.defs.vectors;

import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.IVector;

public interface IFiniteVector extends IVector {

	Map<IFiniteVector, Double> getCoordinates();

	default double[] getGenericCoordinates() throws Throwable {
		final double[] vector = new double[getDim()];
		int i = 0;
		for (final IFiniteVector basevec : getGenericBase()) {
			vector[i] = getCoordinates().get(basevec);
			i++;
		}
		return vector;
	}

	Set<IFiniteVector> getGenericBase() throws Throwable;

//	default List<IFiniteVector> getGenericBase() throws Throwable {
//		return SpaceGenerator.getInstance().
//				getFiniteDimensionalVectorSpace(getDim()).genericBaseToList();
//	}

}
