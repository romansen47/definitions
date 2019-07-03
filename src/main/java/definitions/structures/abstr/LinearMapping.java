package definitions.structures.abstr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;

public abstract class LinearMapping implements Homomorphism {

	protected final VectorSpace source;
	protected final VectorSpace target;
	protected Map<Vector, Map<Vector, Double>> linearity;
	protected double[][] genericMatrix;

	protected LinearMapping(final VectorSpace source, final VectorSpace target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public Map<Vector, Map<Vector, Double>> getLinearity() {
		if (this.linearity == null) {
			this.linearity = new ConcurrentHashMap<>();
			for (final Vector vec1 : ((EuclideanSpace) this.source).genericBaseToList()) {
				final Vector tmp = this.get(vec1);
				final Map<Vector, Double> tmpCoord = new ConcurrentHashMap<>();
				for (final Vector vec2 : ((EuclideanSpace) this.target).genericBaseToList()) {
					tmpCoord.put(vec2, ((EuclideanSpace) this.target).product(vec2, tmp));
				}
				this.linearity.put(vec1, tmpCoord);
			}
		}
		return this.linearity;
	}

	public double[][] getGenericMatrix() {
		if (!((this.source instanceof EuclideanSpace) && (this.target instanceof EuclideanSpace))) {
			return null;
		}
		if (this.genericMatrix == null) {
			this.genericMatrix = new double[((EuclideanSpace) this.getTarget())
					.dim()][((EuclideanSpace) this.getSource()).dim()];
			int i = 0;
			for (final Vector vec1 : ((EuclideanSpace) this.getSource()).genericBaseToList()) {
				int j = 0;
				for (final Vector vec2 : ((EuclideanSpace) this.getTarget()).genericBaseToList()) {
					this.genericMatrix[j][i] = this.getLinearity(vec1).get(vec2);
					j++;
				}
				i++;
			}
		}
		return this.genericMatrix;
	}

	@Override
	public VectorSpace getSource() {
		return this.source;
	}

	@Override
	public VectorSpace getTarget() {
		return this.target;
	}

	@Override
	public Map<Vector, Double> getLinearity(Vector vec1) {
		return this.linearity.get(vec1);
	}
}
