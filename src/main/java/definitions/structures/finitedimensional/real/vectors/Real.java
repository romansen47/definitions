/**
 * 
 */
package definitions.structures.finitedimensional.real.vectors;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.field.impl.RealLine;

/**
 * @author RoManski
 *
 */
public class Real extends Number implements Scalar {

	private static final long serialVersionUID = 448447488896787384L;

	final private double realValue;
	
	private Map <Vector,Scalar> coordinates;
	
	public Real(double value) {
		this.realValue = value;
	}

	@Override
	public int getDim() {
		return 1;
	}

	@Override
	public boolean elementOf(VectorSpace space) {
		return true;
	}

	@Override
	public Boolean equals(Vector vec) {
		return (vec instanceof Real && ((Real) vec).getValue() == this.getValue());
	}

	@Override
	public Map<Vector, Scalar> getCoordinates() {
		if (coordinates==null) {
			coordinates = new HashMap<>();
			coordinates.put(RealLine.getRealLine().getOne(),this);
		}
		return coordinates;
	}

	@Override
	public Scalar[] getGenericCoordinates() {
		return new Scalar[] {this};
	}

	@Override
	public void setCoordinates(Map<Vector, Scalar> coordinates) {
	}

	public double getValue() {
		return this.realValue;
	}

	@Override
	public int intValue() {
		return (int)getValue();
	}

	@Override
	public long longValue() {
		return (long)getValue();
	}

	@Override
	public float floatValue() {
		return (float)getValue();
	}

	@Override
	public double doubleValue() {
		return getValue();
	}
}
