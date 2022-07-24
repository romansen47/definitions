package definitions.structures.abstr.vectorspaces.vectors;

import java.util.Map;

import definitions.settings.XmlPrintable;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface FiniteVectorMethods extends XmlPrintable {

	/**
	 * Method to get the coordinates on the underlying vector space.
	 *
	 * @return the coordinates.
	 * @deprecated vectors do not have coordinates. they may have coordinates with
	 *             respect to a base (and therefore the space this base spans).
	 *             Todo: replace by getCoordinates(EuclideanSpace space);
	 */
	@Deprecated
	Map<Vector, Scalar> getCoordinates();

	/**
	 * Setter for coordinates on a base.
	 *
	 * @param coordinates the coordinates.
	 */
	void setCoordinates(final Map<Vector, Scalar> coordinates);

	void setCoordinates(final Map<Vector, Scalar> coordinates, final EuclideanSpace space);

	@Override
	String toString();

}
