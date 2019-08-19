package definitions.structures.abstr.vectorspaces.vectors;

import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface VectorMethods {

	/**
	 * Method to get the coordinates on the underlying vector space.
	 * 
	 * @return the coordinates.
	 */
	default Map<Vector, Scalar> getCoordinates() {
		return null;
	}

	/**
	 * Coordinates as double[].
	 * 
	 * @return the coordinates as double[].
	 */
//	Scalar[] getGenericCoordinates();

	@Override
	String toString();

	/**
	 * Setter for coordinates on a base.
	 * 
	 * @param coordinates the coordinates.
	 */
	default void setCoordinates(Map<Vector, Scalar> coordinates) {

	}

	default void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {

	}

}
