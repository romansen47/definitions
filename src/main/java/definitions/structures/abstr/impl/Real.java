/**
 * 
 */
package definitions.structures.abstr.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;

/**
 * @author RoManski
 *
 */
public class Real implements Vector {

	final double value;

	public Real(double x) {
		this.value = x;
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
	public Map<Vector, Double> getCoordinates() {
		return null;
	}

	@Override
	public double[] getGenericCoordinates() {
		return null;
	}

	@Override
	public void setCoordinates(Map<Vector, Double> coordinates) {
	}

	public double getValue() {
		return this.value;
	}
}
