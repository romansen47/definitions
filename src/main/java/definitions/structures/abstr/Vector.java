package definitions.structures.abstr;

import java.util.Map;

import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

/**
 * Vector interface.
 * 
 * @author ro
 *
 */
public interface Vector {

	/**
	 * Method to get the dimension of the underlying vector space.
	 * 
	 * @return the dimension of the underlying vector space, if finite dimensional.
	 *         Otherwise null.
	 */
	Integer getDim();

	/**
	 * Method to verify that the vector is contained by a specific vector space.
	 * 
	 * @param space
	 * @return
	 */
	boolean elementOf(VectorSpace space);

	/**
	 * Method to verify, the vector is "essentially" the same as another.
	 * 
	 * @param vec the vector to checl equality for.
	 * @return true if vec is essentially the same as this one.
	 */
	Boolean equals(Vector vec);

	/**
	 * Method to get the coordinates on the underlying vector space.
	 * 
	 * @return the coordinates.
	 */
	Map<Vector, Scalar> getCoordinates();

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
	void setCoordinates(Map<Vector, Scalar> coordinates);

	void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space);

}
