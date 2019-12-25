package definitions.structures.abstr.vectorspaces.vectors;

import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface FiniteVectorMethods extends XmlPrintable {

	/**
	 * Method to get the coordinates on the underlying vector space.
	 * 
	 * @return the coordinates.
	 */
	Map<Vector, Scalar> getCoordinates();

	/**
	 * Setter for coordinates on a base.
	 * 
	 * @param coordinates the coordinates.
	 */
	default void setCoordinates(final Map<Vector, Scalar> coordinates) {

	}

	default void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space) {

	}

	/**
	 * Coordinates as double[].
	 * 
	 * @return the coordinates as double[].
	 */
	// Scalar[] getGenericCoordinates();

	@Override
	String toString();

}
