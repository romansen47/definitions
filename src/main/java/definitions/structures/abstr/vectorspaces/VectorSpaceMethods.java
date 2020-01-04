package definitions.structures.abstr.vectorspaces;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public interface VectorSpaceMethods {

	default void assignOrthonormalCoordinates(final List<Vector> newBase, final Field field) {
		for (final Vector vec : newBase) {
			final Map<Vector, Scalar> tmpCoord = new ConcurrentHashMap<>();
			for (final Vector otherVec : newBase) {
				if (vec == otherVec) {
					tmpCoord.put(otherVec, field.getOne());
				} else {
					tmpCoord.put(otherVec, (Scalar) field.getZero());
				}
			}
			((FiniteVectorMethods) vec).setCoordinates(tmpCoord);
		}
	}

	/**
	 * Not yet implemented.
	 * 
	 * @param vec the vector to check for.
	 * @return whether vec is an element of the space.
	 */
	boolean contains(Vector vec);

	/**
	 * method to get the dimension of the vector space
	 * 
	 * @return null if dimension is infinite. returns the dimension otherwise
	 */
	default Integer getDim() {
		return null;
	}

	/**
	 * Vector space is not empty.
	 * 
	 * @return the zero vector.
	 */
	default Vector nullVec() {
		return null;
	}

	/**
	 * For debugging purposes.
	 * 
	 * @return The string.
	 */
	@Override
	String toString();

}
