package definitions.structures.field.scalar.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.Field;
import definitions.structures.field.impl.ComplexPlane;
import definitions.structures.field.impl.QuaternionSpace;
import definitions.structures.finitedimensional.vectors.FiniteVector;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public class Quaternion implements FiniteVector, Scalar {

	private final Scalar real;
	private final Scalar i;
	private final Scalar j;
	private final Scalar k;

	private Map<Vector, Scalar> coordinates;

	public Quaternion(Scalar re, Scalar i, Scalar j, Scalar k) {
		this.real = re;
		this.i = i;
		this.j = j;
		this.k = k;
	}

	public Quaternion(double r, double i, double j, double k) {
		this(new Real(r), new Real(i), new Real(j), new Real(k));
	}

	@Override
	public Integer getDim() {
		return 4;
	}

	@Override
	public boolean elementOf(VectorSpace space) {
		return space == QuaternionSpace.getInstance();
	}

	@Override
	public Boolean equals(Vector vec) {
		return vec instanceof Quaternion && ((Quaternion) vec).getReal().getValue() == this.getReal().getValue()
				&& ((Quaternion) vec).getI().getValue() == this.getI().getValue()
				&& ((Quaternion) vec).getJ().getValue() == this.getJ().getValue()
				&& ((Quaternion) vec).getK().getValue() == this.getK().getValue();
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		this.coordinates = new HashMap<>();
		this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getOne(), this.getReal());
		this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getI(), this.getI());
		this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getJ(), this.getJ());
		this.coordinates.put(((QuaternionSpace) QuaternionSpace.getInstance()).getK(), this.getK());
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

	@Override
	public String toString() {
		return "( " + this.getReal().getValue() + " ) + i * ( " + this.getI().getValue() + " )" + " + j * ( "
				+ this.getJ().getValue() + " ) + " + "k * ( " + this.getK().getValue() + " )";
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

	/**
	 * @return the i
	 */
	public Scalar getI() {
		return i;
	}

	/**
	 * @return the j
	 */
	public Scalar getJ() {
		return j;
	}

	/**
	 * @return the k
	 */
	public Scalar getK() {
		return k;
	}
}
