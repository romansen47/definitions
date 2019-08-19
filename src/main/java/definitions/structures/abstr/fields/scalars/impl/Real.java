/**
 * 
 */
package definitions.structures.abstr.fields.scalars.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.FiniteVector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class Real extends Number implements Scalar, FiniteVector {

	private static final long serialVersionUID = 448447488896787384L;

	final private double realValue;

	private Map<Vector, Scalar> coordinates;

	private final double eps = 1.e-3;

	public Real(double value) {
		this.realValue = value;
	}

	public Scalar toComplex() {
		return new Complex(this, RealLine.getInstance().getZero());
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	@Override
	public boolean elementOf(VectorSpace space) {
		return space == RealLine.getInstance();
	}

	@Override
	public Boolean equals(Vector vec) {
		return (vec instanceof Real && Math.abs(((Real) vec).getValue() - this.getValue()) < this.eps);
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (this.coordinates == null) {
			this.coordinates = new HashMap<>();
			this.coordinates.put(RealLine.getInstance().getOne(), this);
		}
		return this.coordinates;
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates) {
	}

	@Override
	public double getValue() {
		return this.realValue;
	}

	@Override
	public int intValue() {
		return (int) this.getValue();
	}

	@Override
	public long longValue() {
		return (long) this.getValue();
	}

	@Override
	public float floatValue() {
		return (float) this.getValue();
	}

	@Override
	public double doubleValue() {
		return this.getValue();
	}

	@Override
	public Scalar getInverse() {
		if (Math.abs(this.getValue()) > 1.e-15) {
			return new Real(1 / this.getValue());
		}
		return null;
	}

	@Override
	public String toString() {
		return "" + this.getValue();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.equals(this)) {
			return true;
		}
		return obj instanceof Real && ((Real) obj).getValue() == this.getValue();
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates, EuclideanSpace space) {
	}

	@Override
	public Set<Vector> getGenericBase() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Vector, Scalar> getCoordinates(EuclideanSpace source) {
		return this.getCoordinates();
	}
}
