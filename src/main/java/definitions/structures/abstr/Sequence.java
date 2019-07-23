package definitions.structures.abstr;

import java.util.Map;

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
	public default Map<Vector, Scalar> getCoordinates() {
		return null;
	}

	@Override
	public default Scalar[] getGenericCoordinates() {
		return null;
	}

	@Override
	public default void setCoordinates(Map<Vector, Scalar> coordinates) {
	}

}
