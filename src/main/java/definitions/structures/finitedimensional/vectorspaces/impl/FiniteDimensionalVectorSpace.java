package definitions.structures.finitedimensional.vectorspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.vectors.impl.Tuple;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * 
 * @author RoManski
 *
 *         Conrete implementation of a finite dimensional vector space.
 */
public class FiniteDimensionalVectorSpace implements EuclideanSpace {

	/**
	 * the base.
	 */
	protected volatile List<Vector> base;

	/**
	 * the dimension.
	 */
	protected int dim;

	/**
	 * Plain constructor.
	 */
	protected FiniteDimensionalVectorSpace() {
	}

	/**
	 * Generator using a linear independent set of vectors.
	 * 
	 * @param genericBase the set of vectors.
	 */
	public FiniteDimensionalVectorSpace(final List<Vector> genericBase) {
		this.dim = genericBase.size();
		this.base = genericBase;
	}

	@Override
	public double product(final Vector vec1, final Vector vec2) {
		double prod = 0;
		final Map<Vector, Double> vecCoord1 = vec1.getCoordinates();
		final Map<Vector, Double> vecCoord2 = vec2.getCoordinates();
		final List<Vector> base = this.genericBaseToList();
		for (final Vector vec : vecCoord1.keySet()) {
			prod += vecCoord1.get(vec) * vecCoord2.get(vec);
		}
		return prod;
	}

	@Override
	public boolean contains(final Vector vec) {
		return ((vec instanceof Tuple) && (vec.getDim() == this.dim()));
	}

	@Override
	public Vector nullVec() {
		final Map<Vector, Double> coordinates = new HashMap<>();
		for (final Vector vec : this.genericBaseToList()) {
			coordinates.put(vec, 0.);
		}
		return new Tuple(coordinates);
	}

	@Override
	public List<Vector> genericBaseToList() {
		return this.base;
	}

	@Override
	public int dim() {
		return this.getDim();
	}

	@Override
	public Set<Vector> getGenericBase() {
		return new HashSet<>(this.genericBaseToList());
	}

	/**
	 * setter for the base.
	 * 
	 * @param newBase the new base.
	 */
	protected void setBase(final List<Vector> newBase) {
		this.base = newBase;
	}

	/**
	 * Getter for the dimension.
	 * 
	 * @return the dimension.
	 */
	protected int getDim() {
		return this.dim;
	}

	@Override
	public Vector getCoordinates(final Vector vec) {
		final Map<Vector, Double> coordinates = new HashMap<>();
		for (final Vector baseVec : this.genericBaseToList()) {
			coordinates.put(baseVec, this.product(vec, baseVec));
		}
		if (vec instanceof GenericFunction) {
			return new FunctionTuple(coordinates);
		}
		return this.get(coordinates);
	}

	@Override
	public double getDistance(final Vector ans, final Vector vec2) {
		final Vector diff = this.add(ans, (this.stretch(vec2, -1)));
		return this.norm(diff);
	}

	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		final List<Vector> newBase = new ArrayList<>();
		for (final Vector vec : base) {
			Vector tmp = this.nullVec();
			for (final Vector vec2 : newBase) {
				tmp = this.add(tmp, this.projection(vec, vec2));
			}
			final Vector ans = this.normalize(this.add(vec, this.stretch(tmp, -1)));
			newBase.add(ans);
		}
		return newBase;
	}

	@Override
	public Vector projection(final Vector w, final Vector v) {
		return this.stretch(v, this.product(w, v));
	}

	@Override
	public String toString() {
		String ans = "";
		try {
			for (final Vector vec : this.genericBaseToList()) {
				ans += vec.toString();
			}
			return ans;
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		return super.toString();
	}
}
