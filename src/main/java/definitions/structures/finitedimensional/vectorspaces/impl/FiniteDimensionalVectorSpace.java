package definitions.structures.finitedimensional.vectorspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.Field;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
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

	final private Field field;

	/**
	 * Plain constructor.
	 */
	protected FiniteDimensionalVectorSpace(Field field) {
		this.field = field;
	}

	protected FiniteDimensionalVectorSpace() {
		this.field = RealLine.getInstance();
	}

	/**
	 * Generator using a linear independent set of vectors.
	 * 
	 * @param genericBase the set of vectors.
	 */
	public FiniteDimensionalVectorSpace(Field field, final List<Vector> genericBase) {
		this.field = field;
		this.dim = genericBase.size();
		this.base = genericBase;
	}

	@Override
	public boolean contains(final Vector vec) {
		return ((vec instanceof Tuple) && (vec.getDim() == this.getDim()));
	}

	@Override
	public Vector nullVec() {
		final Map<Vector, Scalar> coordinates = new HashMap<>();
		for (final Vector vec : this.genericBaseToList()) {
			coordinates.put(vec, RealLine.getInstance().getZero());
		}
		return new Tuple(coordinates);
	}

	@Override
	public List<Vector> genericBaseToList() {
		return this.base;
	}

	/**
	 * Getter for the dimension.
	 * 
	 * @return the dimension.
	 */
	@Override
	public Integer getDim() {
		return this.dim;
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
	public void setBase(final List<Vector> newBase) {
		this.base = newBase;
	}

	@Override
	public Vector getCoordinates(final Vector vec) {
		final Map<Vector, Scalar> coordinates = new HashMap<>();
		for (final Vector baseVec : this.genericBaseToList()) {
			coordinates.put(baseVec, this.innerProduct(vec, baseVec));
		}
		if (vec instanceof GenericFunction) {
			return new FunctionTuple(coordinates, this);
		}
		return this.get(coordinates);
	}

	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		final List<Vector> newBase = new ArrayList<>();
		for (final Vector vec : base) {
			Vector tmp = this.nullVec();
			for (final Vector vec2 : newBase) {
				tmp = this.add(tmp, this.projection(vec, vec2));
			}
			final Vector ans = this.normalize(this.add(vec, this.stretch(tmp, new Real(-1))));
			newBase.add(ans);
		}
		return newBase;
	}

	@Override
	public Vector projection(final Vector w, final Vector v) {
		return this.stretch(v, this.innerProduct(w, v));
	}

	@Override
	public String toString() {
		String ans = "";
		try {
			for (final Vector vec : this.genericBaseToList()) {
				ans += vec.toString();
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			return ans;
		}
		return super.toString();
	}

	@Override
	public Field getField() {
		return this.field;
	}
}
