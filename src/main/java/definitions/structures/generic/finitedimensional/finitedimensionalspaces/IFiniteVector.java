package definitions.structures.generic.finitedimensional.finitedimensionalspaces;

import java.util.Map;

import definitions.structures.abstr.IVector;

public interface IFiniteVector extends IVector {
	
	public Map<IVector, Double> getCoordinates();

	Map<IVector, Double> getGenericCoordinates();

}
