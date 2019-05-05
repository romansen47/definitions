package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.Map;

import definitions.structures.abstr.IVector;

public interface IFiniteVector extends IVector {
	
	Map<IFiniteVector, Double> getCoordinates();

	Map<IFiniteVector, Double> getGenericCoordinates();

}
