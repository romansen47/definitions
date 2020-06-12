package definitions.structures.euclidean.vectorspaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.Proceed;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.InnerProductSpace;
import definitions.structures.abstr.vectorspaces.VectorSpaceMethods;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectors.impl.Tuple;

/**
 * 
 * @author RoManski
 *
 *         An euclidean vector space is a finite dimensional hilbert space. It
 *         is equipped with a base. The norm can be used to normalize vectors,
 *         compute distances between vectors and generate an orthonormal base.
 */
public interface EuclideanSpace extends InnerProductSpace, VectorSpaceMethods {

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Proceed
	default Vector addition(final Vector vec1, final Vector vec2) {
		if ((vec1 instanceof FiniteVector) && (vec2 instanceof FiniteVector) && (vec1.getDim().equals(this.getDim()))) {
			final List<Vector> base = this.genericBaseToList();
			final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
			Vector baseVec;
			for (final Vector vec : base) {
				baseVec = this.getBaseVec(vec);
				Vector firstSummand = ((FiniteVector) vec1).getCoordinates().get(this.getBaseVec(vec));
				Vector secondSummand = ((FiniteVector) vec2).getCoordinates().get(this.getBaseVec(vec));
				Scalar add = (Scalar) this.getField().addition(firstSummand, secondSummand);
				coordinates.put(baseVec, add);
			}
			/*
			 * Direct usage of constructor instead of get method in order to avoid cycles.
			 * Don't touch this
			 */
			return new Tuple(coordinates);
		}
		return null;
	}

	/**
	 * A base is an ordered set of linearly independent vectors.
	 * 
	 * @return the base as ordered base. @
	 */
	List<Vector> genericBaseToList();

	/**
	 * Elements of the vector space can be created using a map (Vector - Scalar).
	 * 
	 * @param tmp the coordinates with respect to the base
	 * @return the corresponding vector @
	 */
	default Vector get(final Map<Vector, Scalar> tmp) {
		Vector vec = this.nullVec();
		for (final Vector basevec : this.genericBaseToList()) {
			vec = this.addition(vec, this.stretch(basevec, tmp.get(basevec)));
		}
		return vec;
	}

	/**
	 * Elements of the vector space can be created using a map (Vector - Scalar).
	 * 
	 * @param tmp the coordinates with respect to the base
	 * @return the corresponding vector @
	 */
	@Proceed
	default Vector get(final Scalar[] tmp) {
		Vector vec = this.nullVec();
		for (int i = 0; i < this.getDim(); i++) {
			vec = this.addition(vec, this.stretch(this.genericBaseToList().get(i), tmp[i]));
		}
		return vec;
	}

	/**
	 * identifyequal to and replace by base vector.
	 * 
	 * @param vec the vector to compare to a base vector
	 * @return the base vector, if has same coordinates. Otherwise null.
	 */
	default Vector getBaseVec(final Vector vec) {
		for (final Vector tmp : this.genericBaseToList()) {
			if (vec.equals(tmp)) {
				return tmp;
			}
		}
		return null;
	}

	/**
	 * Method to project a vector. Transformes instances of generic functions to
	 * function tuples in concrete space.
	 * 
	 * @param vec the vector to clone.
	 * @return copy of vec.
	 */
	@Proceed
	Vector getCoordinates(Vector vec);

	/**
	 * The dimension of the space. This is the size of the base.
	 * 
	 * @return the dimension. null if space is infinitely dimensional.
	 */
	@Override
	Integer getDim();

	/**
	 * Method to compute the distance between two vectors.
	 * 
	 * @param vec1 first vector.
	 * @param vec2 second vector.
	 * @return the distance. @
	 */
	@Override
	default Real distance(final Vector vec1, final Vector vec2) {
		final Vector diff = this.addition(vec1, (this.stretch(vec2, this.getField().get(-1))));
		return this.norm(diff);
	}

	EuclideanSpace getDualSpace();

	/**
	 * Method to create an orthonormal base.
	 * 
	 * @param base the original base.
	 * @return an orthonormal base of same span.
	 */
	default List<Vector> getOrthonormalBase(List<Vector> base){
		final List<Vector> newBase = new ArrayList<>();
		for (final Vector vec : base) {
			Vector tmp = this.nullVec();
			for (final Vector vec2 : newBase) {
				tmp = this.addition(tmp, this.projection(vec, vec2));
			}
			final Vector ans = this.normalize(this.addition(vec, this.stretch(tmp, this.getField().get(-1))));
			newBase.add(ans);
		}
		return newBase;
	};

	/**
	 * {@inheritDoc}
	 */
	@Override
	default Scalar innerProduct(final Vector vec1, final Vector vec2) {
		Vector prod = this.getField().nullVec();
		final Map<Vector, Scalar> vecCoord1 = ((FiniteVectorMethods) vec1).getCoordinates();
		final Map<Vector, Scalar> vecCoord2 = ((FiniteVectorMethods) vec2).getCoordinates();
		Vector tmp1;
		Vector tmp2;
		Vector tmpProd;
		for (final Vector vec : vecCoord1.keySet()) {
			tmp1 = vecCoord1.get(vec);
			tmp2 = vecCoord2.get(vec);
			tmpProd = this.getField().product(tmp1, tmp2);
			prod = this.getField().addition(prod, tmpProd);
		}
		return (Scalar) prod;
	}

	/**
	 * Method to show the matrix of scalar products between the base elements.
	 */
	default void show() {
		final List<Vector> base = this.genericBaseToList();
		final Scalar[][] scalarProducts = new Scalar[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				scalarProducts[i][j] = this.innerProduct(vec1, vec2);
				System.out.print((scalarProducts[i][j].toString()) + ",");
				j++;
			}
			System.out.println("");
			i++;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Proceed
	default Vector stretch(final Vector vec, final Scalar r) {
		final ComplexPlane compl = (ComplexPlane) ComplexPlane.getInstance();
		final Map<Vector, Scalar> stretched = new ConcurrentHashMap<>();
		final Map<Vector, Scalar> coordinates = ((FiniteVectorMethods) vec).getCoordinates();
		final List<Vector> base = this.genericBaseToList();
		for (final Vector vec1 : base) {
			final Vector tmpBaseVec = this.getBaseVec(vec1);
			final Vector tmp = coordinates.get(tmpBaseVec);
			final Scalar s = (Scalar) this.getField().multiplication(tmp, r);
			stretched.put(vec1, s);
		}
		return new Tuple(stretched);
	}

}
