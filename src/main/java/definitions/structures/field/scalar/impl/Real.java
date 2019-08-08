/**
 * 
 */
package definitions.structures.field.scalar.impl;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.field.impl.RealLine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

/**
 * @author RoManski
 *
 */
public class Real extends Number implements Scalar {

	private static final long serialVersionUID = 448447488896787384L;

	final private double realValue;

	private Map<Vector, Scalar> coordinates;

	public Real(double value) {
		this.realValue = value;
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
		return (vec instanceof Real && ((Real) vec).getValue() == this.getValue());
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
		if (Math.abs(this.getValue())>1.e-15) {
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
}
