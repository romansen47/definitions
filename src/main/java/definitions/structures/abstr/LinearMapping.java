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

}
