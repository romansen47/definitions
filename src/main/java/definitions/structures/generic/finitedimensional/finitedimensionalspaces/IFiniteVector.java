package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.Map;

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
}
