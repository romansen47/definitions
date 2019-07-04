package definitions.structures.abstr;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * Linear Mapping: mapping between two vector spaces preserving addition and
 * scalar multiplication.
 * 
 * @author ro
 *
 */
public abstract class LinearMapping implements Homomorphism {

	/**
	 * The source vector space.
	 */
	protected final VectorSpace source;

	/**
	 * The target vector space.
	 */
	protected final VectorSpace target;

	/**
	 * The restriction to the base.
	 */
	protected Map<Vector, Map<Vector, Double>> linearity;

	/**
	 * Linearity as a matrix.
	 */
	protected double[][] genericMatrix;

	/**
	 * Constructor. Called by instance of MappingGenerator.
	 * 
	 * @param source the source vector space.
	 * @param target the target vector space.
	 */
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

	@Override
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
