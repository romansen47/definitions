package definitions.structures.abstr;

import java.util.Map;

public abstract class LinearMapping implements Homomorphism {

	protected final VectorSpace source;
	protected final VectorSpace target;
	protected Map<Vector, Map<Vector, Double>> linearity;
	protected double[][] genericMatrix;

	public LinearMapping(final VectorSpace space, final VectorSpace space2) {
		this.source = space;
		this.target = space2;
	}

	@Override
	public Map<Vector, Map<Vector, Double>> getLinearity() throws Throwable {
		return linearity;
	}

	@Override
	public VectorSpace getSource() {
		return source;
	}


	@Override
	public VectorSpace getTarget() {
		return target;
	}
	
	@Override
	public Map<Vector, Double> getLinearity(Vector vec1) throws Throwable {
		return linearity.get(vec1);
	}
}
