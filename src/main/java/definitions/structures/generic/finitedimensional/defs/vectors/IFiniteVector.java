package definitions.structures.generic.finitedimensional.defs.vectors;

import java.util.List;
import java.util.Map;

import definitions.structures.abstr.IVector;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;

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

	default List<IFiniteVector> getGenericBase() throws Throwable {
		return SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(getDim()).genericBaseToList();
	}

}
