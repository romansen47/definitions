package definitions.structures.generic.finitedimensional.defs.vectors;

import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;

public interface FiniteVector extends Vector {

	Map<FiniteVector, Double> getCoordinates();

	default double[] getGenericCoordinates() throws Throwable {
		final double[] vector = new double[getDim()];
		int i = 0;
		for (final FiniteVector basevec : getGenericBase()) {
			vector[i] = getCoordinates().get(basevec);
			i++;
		}
		return vector;
	}

	Set<FiniteVector> getGenericBase() throws Throwable;

}
