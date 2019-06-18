package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalVectorSpace implements EuclideanSpace {

	protected List<Vector> base;

	protected int dim;

	protected FiniteDimensionalVectorSpace() throws Throwable {
	}

	public FiniteDimensionalVectorSpace(final List<Vector> genericBase) throws Throwable {
		this.dim = genericBase.size();
		this.base = genericBase;
	}

	@Override
	public double product(final Vector vec1, final Vector vec2) throws Throwable {
		if (!((vec1 instanceof Tuple) && (vec2 instanceof Tuple))) {
			throw new Throwable();
		}
		double prod = 0;
		final Map<Vector, Double> vecCoord1 = vec1.getCoordinates();
		final Map<Vector, Double> vecCoord2 = vec2.getCoordinates();
		final List<Vector> base = this.genericBaseToList();
		for (final Vector vec : base) {
			prod += vecCoord1.get(this.getBaseVec(vec)) * vecCoord2.get(this.getBaseVec(vec));
		}
		return prod;
	}

	@Override
	public boolean contains(final Vector vec) throws Throwable {
		return ((vec instanceof Tuple) && (vec.getDim() == this.dim()));
	}

	@Override
	public Vector nullVec() throws Throwable {
		final Map<Vector, Double> coordinates = new HashMap<>();
		for (final Vector vec : this.genericBaseToList()) {
			coordinates.put(vec, 0.);
		}
		return new Tuple(coordinates);
	}

	@Override
	public List<Vector> genericBaseToList() throws Throwable {
		return this.getBase();
	}

	@Override
	public int dim() throws Throwable {
		return this.getDim();
	}

	@Override
	public Set<Vector> getGenericBase() throws Throwable {
		return new HashSet<>(this.genericBaseToList());
	}

	/**
	 * @return the base
	 */
	public List<Vector> getBase() {
		return this.base;
	}

	protected void setBase(final List<Vector> newBase) {
		this.base = newBase;
	}

	/**
	 * @return the dim
	 */
	protected int getDim() {
		return this.dim;
	}

	@Override
	public Vector getCoordinates(final Vector vec) throws Throwable {
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
	public double getDistance(final Vector ans, final Vector vec2) throws Throwable {
		final Vector diff = this.add(ans, (this.stretch(vec2, -1)));
		return this.norm(diff);
	}

	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) throws Throwable {
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
	public Vector projection(final Vector w, final Vector v) throws Throwable {
		return this.stretch(v, this.product(w, v));
	}

	@Override
	public String toString() {
		String ans = "";
		for (final Vector vec : this.getBase()) {
			ans += vec.toString();
		}
		return ans;
	}
}
