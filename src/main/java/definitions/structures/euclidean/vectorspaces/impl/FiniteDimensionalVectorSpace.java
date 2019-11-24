package definitions.structures.euclidean.vectorspaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
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
 
	protected Field field;

	/**
	 * Plain constructor.
	 */
	protected FiniteDimensionalVectorSpace(Field field) {
		this.field = field;
	}

	public FiniteDimensionalVectorSpace() {
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
		System.out.println("Created new " + genericBase.size() + "-dimensional euclidean space.");
	}

	@Override
	public boolean contains(final Vector vec) {
		return ((vec instanceof Tuple) && (vec.getDim().equals(this.getDim())));
	}

	@Override
	public Vector nullVec() {
		final Map<Vector, Scalar> coordinates = new HashMap<>();
		for (final Vector vec : this.genericBaseToList()) {
			coordinates.put(vec, (Scalar) this.getField().getZero());
		}
		/*
		 * Direct usage of constructor instead of get method in order to avoid cycles.
		 * Don't touch this
		 */
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
			final Vector ans = this.normalize(this.add(vec, this.stretch(tmp, this.getField().get(-1))));
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
	
	public void show() {
		EuclideanSpace.super.show();
	}
	
	@Override
	public Field getField() {
		return this.field;
	}

	@Override
	public EuclideanSpace getDualSpace() {
		if (this.dualSpace == null) {
			this.dualSpace = new FunctionalSpace(this);
		}
		return this.dualSpace;
	}

	@Override
	public String toXml() {
		String ans = "<"+this.getClass()+">";
		ans += "<base>";
		for (Vector baseVec:genericBaseToList()) {
			ans+=baseVec.toXml();
		}
		ans += "</base>";
		ans = "</"+this.getClass().toString().split("class ")[1]+">";
		return ans;
	}

	/*
	 * These overrides are for tracing purposes only
	 */

//	@Override
//	
//	public Vector add(final Vector vec1, final Vector vec2) {
//		return EuclideanSpace.super.add(vec1, vec2);
//	}
//
//	@Override
//	
//	public Vector stretch(final Vector vec, final Scalar r) {
//		return EuclideanSpace.super.stretch(vec, r);
//	}

}
