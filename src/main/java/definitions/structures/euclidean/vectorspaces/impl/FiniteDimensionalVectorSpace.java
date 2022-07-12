package definitions.structures.euclidean.vectorspaces.impl;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
		this.dim = genericBase.size();
		this.base = genericBase;
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
		return this.base;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FiniteVector getCoordinates(final Vector vec) {
		final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
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
		return this.dim;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EuclideanSpace getDualSpace() {
		if (this.dualSpace == null) {
			this.dualSpace = new FunctionalSpace(this);
		}
		return this.dualSpace;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@Proceed
	public Field getField() {
		return this.field;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector nullVec() {
		final Map<Vector, Scalar> coordinates = new ConcurrentHashMap<>();
		for (final Vector vec : this.genericBaseToList()) {
			coordinates.put(vec, this.getField().getZero());
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
		this.base = newBase;
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
		StringBuilder s = new StringBuilder("<" + clazz + ">\r");
		s.append("<base>");
		for (final Vector baseVec : this.genericBaseToList()) {
			s.append("<baseVec>" + this.genericBaseToList().indexOf(baseVec) + "</baseVec>\r");
		}
		s.append("</base>");
		s.append("</" + clazz + ">");
		return s.toString();
	}

}
