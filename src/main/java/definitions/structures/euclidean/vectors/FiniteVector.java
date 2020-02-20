package definitions.structures.euclidean.vectors;

import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * Finite vector.
 * 
 * @author ro
 *
 */
public interface FiniteVector extends Vector, FiniteVectorMethods {

	/**
	 * {@inheritDoc}
	 */
	@Override
	Map<Vector, Scalar> getCoordinates();

	/**
	 * Method to compute the coordinates of the vector with respect to the base of a
	 * vector space.
	 * 
	 * @param source the vector space.
	 * @return the coordinates.
	 */
	default Map<Vector, Scalar> getCoordinates(EuclideanSpace source){
		return getCoordinates();
	};

	/**
	 * Method to compute the projection of the vector onto a vector space.
	 * 
	 * @param source the vector space.
	 * @return the projection.
	 */
	default Function getProjection(final EuclideanSpace source) {
		return new FunctionTuple(this.getCoordinates(source), source);
	}

	@Override
	void setCoordinates(Map<Vector, Scalar> coordinates);

	@Override
	void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space);

}
