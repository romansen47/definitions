package definitions.structures.abstr.vectorspaces.vectors;

import java.io.Serializable;
import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import settings.annotations.Proceed;

public interface FiniteVectorMethods extends XmlPrintable, Serializable {

	/**
	 * Method to get the coordinates on the underlying vector space.
	 * 
	 * @return the coordinates.
	 */
	@Proceed
	Map<Vector, Scalar> getCoordinates();

	/**
	 * Coordinates as double[].
	 * 
	 * @return the coordinates as double[].
	 */
	// Scalar[] getGenericCoordinates();

	@Override
	@Proceed
	String toString();

	/**
	 * Setter for coordinates on a base.
	 * 
	 * @param coordinates the coordinates.
	 */
	@Proceed
	default void setCoordinates(Map<Vector, Scalar> coordinates) {

	}

	@Proceed
	default void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {

	}

}
