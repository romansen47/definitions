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

	/**
	 * The corresponding field.
	 */
	private Field field;

	public FiniteDimensionalVectorSpace() {
		this.setField(RealLine.getInstance());
	}

	/**
	 * Plain constructor.
	 *
	 * @param field the basic field
	 */
	protected FiniteDimensionalVectorSpace(final Field field) {
		this.setField(field);
	}

	/**
	 * Generator using a linear independent set of vectors.
	 *
	 * @param genericBase the set of vectors.
	 * @param field       the basic field
	 */
	public FiniteDimensionalVectorSpace(final Field field, final List<Vector> genericBase) {
		this.setField(field);
		dim = genericBase.size();
		base = genericBase;
	}

	/**
	 * checks whether the vector is an element of this space
	 *
	 * @param vec the given vector
	 * @return true if given vector is an element of the given space
	 * @deprecated it should be discussed if we should throw this away. soon
	 */
	@Deprecated
	@Override
	public boolean contains(final Vector vec) {
		return ((vec instanceof Tuple) && (vec.getDim().equals(this.getDim())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Vector> genericBaseToList() {
		return base;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FiniteVector getCoordinates(final Vector vec) {
		final Map<Vector, Scalar> coordinates = new HashMap<>();
		for (final Vector baseVec : this.genericBaseToList()) {
			coordinates.put(baseVec, this.innerProduct(vec, baseVec));
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EuclideanSpace getDualSpace() {
		if (dualSpace == null) {
			dualSpace = new FunctionalSpace(this);
		}
		return dualSpace;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Proceed
	public Field getField() {
		return field;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector nullVec() {
		final Map<Vector, Scalar> coordinates = new HashMap<>();
		for (final Vector vec : this.genericBaseToList()) {
			coordinates.put(vec, (Scalar) this.getField().getZero());
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

	/**
	 * setter for the field
	 *
	 * @param field the corresponding field
	 */
	public void setField(final Field field) {
		this.field = field;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void show() {
		EuclideanSpace.super.show();
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		final String clazz = this.getClass().toString().split("class ")[1];
		String ans = "<" + clazz + ">\r";
		ans += "<base>";
		for (final Vector baseVec : this.genericBaseToList()) {
			ans += "<baseVec>" + this.genericBaseToList().indexOf(baseVec) + "</baseVec>\r";
		}
		ans += "</base>";
		ans += "</" + clazz + ">";
		return ans;
	}

}
