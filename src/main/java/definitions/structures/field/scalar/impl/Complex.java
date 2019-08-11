package definitions.structures.field.scalar.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.Field;
import definitions.structures.field.impl.ComplexPlane;
import definitions.structures.finitedimensional.vectors.FiniteVector;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public class Complex implements FiniteVector, Scalar {

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
	public Boolean equals(Vector vec) {
		return vec instanceof Complex && ((Complex) vec).getReal().getValue() == this.getReal().getValue()
				&& ((Complex) vec).getImag().getValue() == this.getImag().getValue();
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
		return 0;
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

	@Override
	public String toString() {
		return "( " + this.getReal().getValue() + " ) + i * ( " + this.getImag().getValue() + " )";
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
