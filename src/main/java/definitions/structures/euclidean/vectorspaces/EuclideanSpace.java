package definitions.structures.euclidean.vectorspaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;

import definitions.Proceed;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.InnerProductSpace;
import definitions.structures.abstr.vectorspaces.VectorSpaceMethods;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectors.impl.Tuple;
import exceptions.DevisionByZeroException;

/**
 * An euclidean vector space is a finite dimensional hilbert space. It is
 * equipped with a base. The scalar product can be used to normalize vectors,
 * compute angles and distances between vectors and generate an orthonormal
 * base.
 *
 * @author RoManski
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
			base.stream().forEach(vec -> { // no parallel stream here?
				final Vector baseVec = this.getBaseVec(vec);
				final Vector firstSummand = ((FiniteVector) vec1).getCoordinates().get(this.getBaseVec(vec));
				final Vector secondSummand = ((FiniteVector) vec2).getCoordinates().get(this.getBaseVec(vec));
				final Scalar add = (Scalar) this.getField().addition(firstSummand, secondSummand);
				coordinates.put(baseVec, add);
			});
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
		LogManager.getLogger(EuclideanSpace.class).error("no corresponding base vector found for {}", vec);
		return null;
	}

	/**
	 * Method to project a vector. Transformes instances of generic functions to
	 * function tuples in concrete space.
	 *
	 * @param vec the vector to project.
	 * @return projection of vec.
	 */
	@Proceed
	Vector getProjection(Vector vec);

	/**
	 * The dimension of the space. This is the size of the base.
	 *
	 * @return the dimension. null if space is infinitely dimensional.
	 */
	@Override
	Integer getDim();

	EuclideanSpace getDualSpace();

	/**
	 * Method to create an orthonormal base.
	 *
	 * @param base the original base.
	 * @return an orthonormal base of same span.
	 * @throws DevisionByZeroException if devision by zero occures
	 */
	default List<Vector> getOrthonormalBase(List<Vector> base) throws DevisionByZeroException {
		final List<Vector> newBase = new ArrayList<>();
		for (final Vector vec : base) {
			if (newBase.isEmpty()) {
				newBase.add(this.normalize(vec));
			} else {
				Vector tmp = this.nullVec();
				for (final Vector vec2 : newBase) {
					tmp = this.addition(tmp, this.projection(vec, vec2));
				}
				final Vector ans = this.addition(vec, this.stretch(tmp, this.getField().get(-1)));
				newBase.add(this.normalize(ans));
			}
		}
		return newBase;
	}

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
	 *
	 * @return an matrix of the products of the base elements
	 */
	default Scalar[][] show() {
		final List<Vector> base = this.genericBaseToList();
		final Scalar[][] scalarProducts = new Scalar[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			StringBuilder s = new StringBuilder();
			for (final Vector vec2 : base) {
				scalarProducts[i][j] = this.innerProduct(vec1, vec2);
				s.append(scalarProducts[i][j] + " ");
				j++;
			}
			LogManager.getLogger(EuclideanSpace.class).debug(s);
			i++;
		}
		return scalarProducts;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Proceed
	default Vector stretch(final Vector vec, final Scalar r) {
		final Map<Vector, Scalar> stretched = new ConcurrentHashMap<>();
		final Map<Vector, Scalar> coordinates = ((FiniteVectorMethods) vec).getCoordinates();
		final List<Vector> base = this.genericBaseToList();
		base.stream().forEach(vec1 -> {
			final Vector tmpBaseVec = this.getBaseVec(vec1);
			final Vector tmp = coordinates.get(tmpBaseVec);
			final Scalar s = (Scalar) this.getField().multiplication(tmp, r);
			stretched.put(vec1, s);
		});
		return new Tuple(stretched);
	}

}
