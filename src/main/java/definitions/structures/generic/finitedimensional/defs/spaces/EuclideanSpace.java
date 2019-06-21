package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.HilbertSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

/**
 * 
 * @author RoManski
 *
 *         An euclidean vector space is a finite dimensional hilbert space. It
 *         is equipped with a base. The norm can be used to normalize vectors,
 *         compute distances between vectors and generate an orthonormal base.
 */
public interface EuclideanSpace extends HilbertSpace {

	/**
	 * A base is an ordered set of linearly independent vectors.
	 * 
	 * @return the base as ordered base.
	 * @throws Throwable
	 */
	List<Vector> genericBaseToList() throws Throwable;

	/**
	 * The base can be returned as an unordered set.
	 * 
	 * @return the base as a set.
	 * @throws Throwable
	 */
	Set<Vector> getGenericBase() throws Throwable;

	/**
	 * The dimension of the space. This is the size of the base.
	 * 
	 * @return
	 * @throws Throwable
	 */
	int dim() throws Throwable;

	/**
	 * Elements of the vector space can be created using a map (Vector -> double).
	 * 
	 * @param tmp the coordinates with respect to the base
	 * @return the corresponding vector
	 * @throws Throwable
	 */
	default Vector get(final Map<Vector, Double> tmp) throws Throwable {
		Vector vec = nullVec();
		for (final Vector basevec : genericBaseToList()) {
			vec = add(vec, stretch(basevec, tmp.get(basevec).doubleValue()));
		}
		return vec;
	}

	@Override
	default Vector add(final Vector vec1, final Vector vec2) throws Throwable {
		if ((vec1 instanceof FiniteVector) && (vec2 instanceof FiniteVector) && (vec1.getDim() == vec2.getDim())
				&& (vec1.getDim() == dim())) {
			final List<Vector> base = genericBaseToList();
			final Map<Vector, Double> coordinates = new ConcurrentHashMap<>();
			for (final Vector vec : base) {
				coordinates.put(getBaseVec(vec), ((FiniteVector) vec1).getCoordinates().get(getBaseVec(vec))
						+ ((FiniteVector) vec2).getCoordinates().get(getBaseVec(vec)));
			}
			return new Tuple(coordinates);
		}
		return null;
	}

	@Override
	default Vector stretch(final Vector vec, final double r) throws Throwable {
		final Map<Vector, Double> stretched = new ConcurrentHashMap<>();
		final Map<Vector, Double> coordinates = ((FiniteVector) vec).getCoordinates();
		final List<Vector> base = genericBaseToList();
		for (final Vector vec1 : base) {
			stretched.put(vec1, coordinates.get(vec1) * r);
		}
		return new Tuple(stretched);
	}

	/**
	 * Compare vectors in order to identify base vectors.
	 * 
	 * @param vec2
	 * @return the base vector, if has same coordinates. Otherwise null.
	 * @throws Throwable
	 */
	default Vector getBaseVec(final Vector vec2) throws Throwable {
		for (final Vector vec : genericBaseToList()) {
			if (vec2.equals(vec)) {
				return vec;
			}
		}
		return null;
	}

	/**
	 * Method to clone a vector.
	 * 
	 * @param vec the vector to clone.
	 * @return copy of vec.
	 * @throws Throwable
	 */
	Vector copyVector(Vector vec) throws Throwable;

	/**
	 * Method to compute the distance between two vectors.
	 * 
	 * @param vec1 first vector.
	 * @param vec2 second vector.
	 * @return the distance.
	 * @throws Throwable
	 */
	default double getDistance(final Vector vec1, final Vector vec2) throws Throwable {
		final Vector diff = add(vec1, (stretch(vec2, -1)));
		return norm(diff);
	}

	/**
	 * Method to create an orthonormal base.
	 * 
	 * @param the original base.
	 * @return an orthonormal base of same span.
	 * @throws Throwable
	 */
	List<Vector> getOrthonormalBase(List<Vector> base) throws Throwable;

}
