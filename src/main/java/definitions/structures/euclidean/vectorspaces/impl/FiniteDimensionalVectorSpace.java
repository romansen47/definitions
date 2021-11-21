package definitions.structures.euclidean.vectorspaces.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.Proceed;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectors.impl.FunctionTuple;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.impl.Tuple;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 *
 * @author RoManski
 *
 *         Conrete implementation of a finite dimensional vector space.
 */

public class FiniteDimensionalVectorSpace implements EuclideanSpace {

	private static final long serialVersionUID = -7678979021442720279L;

	private EuclideanSpace dualSpace;

	/**
	 * the base.
	 */
	protected List<Vector> base;

	/**
	 * the dimension.
	 */
	protected int dim;

	private Field field;

	public FiniteDimensionalVectorSpace() {
		setField(RealLine.getInstance());
	}

	/**
	 * Plain constructor.
	 *
	 * @param field the basic field
	 */
	protected FiniteDimensionalVectorSpace(final Field field) {
		setField(field);
	}

	/**
	 * Generator using a linear independent set of vectors.
	 *
	 * @param genericBase the set of vectors.
	 * @param field       the basic field
	 */
	public FiniteDimensionalVectorSpace(final Field field, final List<Vector> genericBase) {
		setField(field);
		dim = genericBase.size();
		base = genericBase;
	}

	@Override
	public boolean contains(final Vector vec) {
		return ((vec instanceof Tuple) && (vec.getDim().equals(getDim())));
	}

	@Override
	public List<Vector> genericBaseToList() {
		return base;
	}

	@Override
	public FiniteVector getCoordinates(final Vector vec) {
		final Map<Vector, Scalar> coordinates = new HashMap<>();
		for (final Vector baseVec : genericBaseToList()) {
			coordinates.put(baseVec, innerProduct(vec, baseVec));
		}
		if (vec instanceof GenericFunction) {
			return new FunctionTuple(coordinates, this);
		}
		return (FiniteVector) this.get(coordinates);
	}

	/**
	 * Getter for the dimension.
	 *
	 * @return the dimension.
	 */
	@Override
	public Integer getDim() {
		return dim;
	}

	@Override
	public EuclideanSpace getDualSpace() {
		if (dualSpace == null) {
			dualSpace = new FunctionalSpace(this);
		}
		return dualSpace;
	}

	@Override
	@Proceed
	public Field getField() {
		return field;
	}

//	@Override
//	public List<Vector> getOrthonormalBase(final List<Vector> base) {
//		final List<Vector> newBase = new ArrayList<>();
//		for (final Vector vec : base) {
//			Vector tmp = this.nullVec();
//			for (final Vector vec2 : newBase) {
//				tmp = this.addition(tmp, this.projection(vec, vec2));
//			}
//			final Vector ans = this.normalize(this.addition(vec, this.stretch(tmp, this.getField().get(-1))));
//			newBase.add(ans);
//		}
//		return newBase;
//	}

	@Override
	public Vector nullVec() {
		final Map<Vector, Scalar> coordinates = new HashMap<>();
		for (final Vector vec : genericBaseToList()) {
			coordinates.put(vec, (Scalar) getField().getZero());
		}
		/*
		 * Direct usage of constructor instead of get method in order to avoid cycles.
		 * Don't touch this.
		 */
		return new Tuple(coordinates);
	}

	/**
	 * setter for the base.
	 *
	 * @param newBase the new base.
	 */
	public void setBase(final List<Vector> newBase) {
		base = newBase;
	}

	public void setField(final Field field) {
		this.field = field;
	}

	@Override
	public void show() {
		EuclideanSpace.super.show();
	}

	@Override
	public String toString() {
		String ans = "";
		try {
			for (final Vector vec : genericBaseToList()) {
				ans += vec.toString();
			}
		} catch (final Throwable e) {
			e.printStackTrace();
			return ans;
		}
		return super.toString();
	}

	@Override
	public String toXml() {
		final String clazz = this.getClass().toString().split("class ")[1];
		String ans = "<" + clazz + ">\r";
		ans += "<base>";
		for (final Vector baseVec : genericBaseToList()) {
			ans += "<baseVec>" + genericBaseToList().indexOf(baseVec) + "</baseVec>\r";
		}
		ans += "</base>";
		ans += "</" + clazz + ">";
		return ans;
	}

//	/*
//	 * These overrides are for tracing purposes only
//	 */
//
//	@Override
//	public Vector add(final Vector vec1, final Vector vec2) {
//		return EuclideanSpace.super.add(vec1, vec2);
//	}
//
//	@Override
//	public Vector stretch(final Vector vec, final Scalar r) {
//		return EuclideanSpace.super.stretch(vec, r);
//	}

}
