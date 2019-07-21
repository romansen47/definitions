package definitions.structures.abstr.impl;

import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;

public interface Sequence extends Vector {

	Vector getValue(int n);
	
	@Override
	public default int getDim() {
		return -1;
	}

	@Override
	public default boolean elementOf(VectorSpace space) {
		return space instanceof SequenceSpace;
	}

	@Override
	public default Boolean equals(Vector vec) {
		return null;
	}

	@Override
	public default Map<Vector, Double> getCoordinates() {
		return null;
	}

	@Override
	public default double[] getGenericCoordinates() {
		return null;
	}

	@Override
	public default void setCoordinates(Map<Vector, Double> coordinates) {
	}

}
