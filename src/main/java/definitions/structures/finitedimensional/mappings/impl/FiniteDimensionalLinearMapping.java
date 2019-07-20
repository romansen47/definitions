package definitions.structures.finitedimensional.mappings.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.InnerProductSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.abstr.impl.LinearMapping;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

/**
 * Finite dimensional linear mapping
 * 
 * @author ro
 *
 */
public class FiniteDimensionalLinearMapping extends LinearMapping implements FiniteDimensionalHomomorphism {

	/**
	 * Constructor.
	 * 
	 * @param source      the source vector space.
	 * @param target      the target vector space.
	 * @param coordinates the coordinates mapping.
	 */
	public FiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Double>> coordinates) {
		super(source, target);
		this.linearity = coordinates;
		this.genericMatrix = new double[((EuclideanSpace) this.getTarget()).dim()][((EuclideanSpace) this.getSource())
				.dim()];
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			for (final Vector vec2 : target.genericBaseToList()) {
				this.genericMatrix[j][i] = this.getLinearity(vec1).get(vec2);
				j++;
			}
			i++;
		}
	}

	public FiniteDimensionalLinearMapping(EuclideanSpace source, EuclideanSpace target) {
		super(source, target);
	}

	@Override
	public String toString() {
		String str = "";
		double[][] matrix;
		try {
			matrix = this.getGenericMatrix();
			double x;
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[i].length; j++) {
					x = matrix[i][j];
					str += " " + (x - (x % 0.001)) + " ";
				}
				str += " \r";
			}
		} catch (final Throwable e) {
			e.printStackTrace();
		}
		return str;
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
			if (this.linearity == null) {
				this.linearity = new HashMap<>();
				if (this instanceof Function) {
					for (final Vector sourceVec : ((EuclideanSpace) this.getSource()).genericBaseToList()) {
						this.linearity.put(sourceVec,
								((Function) this.get(sourceVec)).getCoordinates((EuclideanSpace) this.target));
					}
				} else {
					for (final Vector sourceVec : ((EuclideanSpace) this.getSource()).genericBaseToList()) {
						Map<Vector, Double> tmp = new HashMap<>();
						for (final Vector targetVec : ((EuclideanSpace) this.getTarget()).genericBaseToList()) {
							tmp.put(targetVec,
									((InnerProductSpace) this.getTarget()).product(this.get(sourceVec), targetVec));
						}
						this.linearity.put(sourceVec, tmp);
					}
				}
			}
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
