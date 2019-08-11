package definitions.structures.finitedimensional.vectors;

import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * Finite vector.
 * 
 * @author ro
 *
 */
public interface FiniteVector extends Vector {

	@Override
	Map<Vector, Scalar> getCoordinates();

//	@Override
//	default Scalar[] getGenericCoordinates() {
//		final Scalar[] vector = new Scalar[getDim()];
//		int i = 0;
//		for (final Vector basevec : ((EuclideanSpace) SpaceGenerator.getInstance()
//				.getFiniteDimensionalVectorSpace(getDim())).genericBaseToList()) {
//			vector[i] = getCoordinates().get(basevec);
//			i++;
//		}
//		return vector;
//	}

	/**
	 * Method to get the base corresponding to the coordinates of the vector.
	 * 
	 * @return the base.
	 */
	Set<Vector> getGenericBase();

	/**
	 * Method to compute the coordinates of the vector with respect to the base of a
	 * vector space.
	 * 
	 * @param source the vector space.
	 * @return the coordinates.
	 */
	Map<Vector, Scalar> getCoordinates(EuclideanSpace source);

	/**
	 * Method to compute the projection of the vector onto a vector space.
	 * 
	 * @param source the vector space.
	 * @return the projection.
	 */
	default Function getProjection(EuclideanSpace source) {
		return new FunctionTuple(getCoordinates(source), source);
	}

	@Override
	void setCoordinates(Map<Vector, Scalar> coordinates);

	@Override
	void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space);

}
