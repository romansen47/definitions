package definitions.structures.abstr.fields.scalars.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class Complex implements FiniteVector, Scalar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160805146914088274L;
	private final Scalar real;
	private final Scalar imag;
	private Map<Vector, Scalar> coordinates;

	public Complex(Scalar re, Scalar im) {
		this.real = re;
		this.imag = im;
	}

	public Complex(double x, double y) {
		this(new Real(x), new Real(y));
	}

	@Override
	public Integer getDim() {
		return 2;
	}

	@Override
	public boolean elementOf(VectorSpace space) {
		return space == ComplexPlane.getInstance();
	}

	@Override
	public boolean equals(Object vec) {
		return vec instanceof Complex && ((Complex) vec).getReal().equals(this.getReal())
				&& ((Complex) vec).getImag().equals(this.getImag());
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		this.coordinates = new HashMap<>();
		this.coordinates.put(((Field) ComplexPlane.getInstance()).getOne(), this.getReal());
		this.coordinates.put(((ComplexPlane) ComplexPlane.getInstance()).getI(), this.getImag());
		return this.coordinates;
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates) {
		this.coordinates = coordinates;
	}

	@Override
	public double getValue() {
		return this.getReal().getValue();
	}

	@Override
	public Scalar getInverse() {
		return (Scalar) ((Field) ComplexPlane.getInstance()).inverse(this);
	}

	/**
	 * @return the real
	 */
	public Scalar getReal() {
		return this.real;
	}

	/**
	 * @return the imag
	 */
	public Scalar getImag() {
		return this.imag;
	}

	// @Override
	// public String toString() {
	// return "( " + this.getReal().getValue() + " ) + i * ( " +
	// this.getImag().getValue() + " )";
	// }

	@Override
	public String toString() {
		return "1  ->  " + this.getReal().getValue() + "\r" + "i  ->  " + this.getImag().getValue() + "\r";
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
	}

	@Override
	public Set<Vector> getGenericBase() {
		return ComplexPlane.getInstance().getGenericBase();
	}

	@Override
	public Map<Vector, Scalar> getCoordinates(EuclideanSpace source) {
		return this.getCoordinates();
	}
}
