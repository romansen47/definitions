package definitions.structures.abstr;

import java.util.Map;

public interface SequenceSpace extends VectorSpace {
	
	VectorSpace getTargetSpace();
	
	@Override
	public default boolean contains(Vector vec) {
		return vec instanceof Sequence;
	}

	@Override
	public default Vector nullVec() {
		return new Sequence() {

			@Override
			public Integer getDim() {
				return -1;
			}

			@Override
			public boolean elementOf(VectorSpace space) {
				return space instanceof SequenceSpace;
			}

			@Override
			public Map<Vector, Scalar> getCoordinates() {
				return null;
			}

			@Override
			public Scalar[] getGenericCoordinates() {
				return null;
			}

			@Override
			public void setCoordinates(Map<Vector, Scalar> coordinates) {
			}

			@Override
			public Vector getValue(int n) {
				return getTargetSpace().nullVec();
			}

			@Override
			public Boolean equals(Vector vec) {
				return null;
			}
			
		};
	}

	@Override
	public default Vector add(Vector vec1, Vector vec2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public default Vector stretch(Vector vec1, Scalar r) {
		// TODO Auto-generated method stub
		return null;
	}

}
