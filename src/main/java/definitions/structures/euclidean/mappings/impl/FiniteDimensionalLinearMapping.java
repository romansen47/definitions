package definitions.structures.euclidean.mappings.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.impl.LinearMapping;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * Finite dimensional linear mapping
 *
 * @author ro
 *
 */
public class FiniteDimensionalLinearMapping extends LinearMapping implements FiniteDimensionalHomomorphism {

	public FiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target) {
		super(source, target);
	}

	/**
	 * Constructor.
	 *
	 * @param source      the source vector space.
	 * @param target      the target vector space.
	 * @param coordinates the coordinates mapping.
	 */
	public FiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinates) {
		super(source, target);
		linearity = coordinates;
		genericMatrix = new Scalar[((EuclideanSpace) getTarget()).getDim()][((EuclideanSpace) getSource()).getDim()];
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			for (final Vector vec2 : target.genericBaseToList()) {
				genericMatrix[j][i] = getImageVectorOfBaseVector(source.getBaseVec(vec1)).get(target.getBaseVec(vec2));
				j++;
			}
			i++;
		}
	}

	public FiniteDimensionalLinearMapping(final EuclideanSpace source, final EuclideanSpace target,
			final Map<Vector, Map<Vector, Scalar>> coordinates, final Scalar[][] matrix) {
		this(source, target, coordinates);
		genericMatrix = matrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar[][] getGenericMatrix() {
		if (!((source instanceof EuclideanSpace) && (target instanceof EuclideanSpace))) {
			return null;
		}
		if (genericMatrix == null) {
			if (linearity == null) {
				linearity = new ConcurrentHashMap<>();
				for (final Vector sourceVec : ((EuclideanSpace) getSource()).genericBaseToList()) {
					linearity.put(sourceVec, ((Function) this.get(sourceVec)).getCoordinates((EuclideanSpace) target));
				}
			}
			genericMatrix = new Scalar[((EuclideanSpace) getTarget()).getDim()][((EuclideanSpace) getSource())
					.getDim()];
			int i = 0;
			for (final Vector vec1 : ((EuclideanSpace) getSource()).genericBaseToList()) {
				int j = 0;
				for (final Vector vec2 : ((EuclideanSpace) getTarget()).genericBaseToList()) {
					genericMatrix[j][i] = getImageVectorOfBaseVector(vec1).get(vec2);
					j++;
				}
				i++;
			}
		}
		return genericMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Scalar> getImageVectorOfBaseVector(final Vector vec1) {
		return linearity.get(vec1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, Map<Vector, Scalar>> getLinearity() {
		if (linearity == null) {
			linearity = new ConcurrentHashMap<>();
			for (final Vector vec1 : ((EuclideanSpace) source).genericBaseToList()) {
				final Vector tmp = this.get(vec1);
				final Map<Vector, Scalar> tmpCoord = new ConcurrentHashMap<>();
				for (final Vector vec2 : ((EuclideanSpace) target).genericBaseToList()) {
					tmpCoord.put(vec2, ((EuclideanSpace) target).innerProduct(vec2, tmp));
				}
				linearity.put(vec1, tmpCoord);
			}
		}
		return linearity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpace getSource() {
		return source;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VectorSpace getTarget() {
		return target;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		Scalar[][] matrix;
		matrix = getGenericMatrix();
		double x;
		for (final Scalar[] element : matrix) {
			for (int j = 0; j < element.length; j++) {
				x = ((Real) element[j]).getDoubleValue();
				str.append(" " + (x - (x % 0.001)) + " ");
			}
			str.append(" \r");
		}
		return str.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		final StringBuilder builder = new StringBuilder();
		builder.append("<linearMapping>");
		builder.append("<source>" + source.toXml() + "</source>");
		builder.append("<target>" + target.toXml() + "</target>");
		builder.append("<base>");
		for (final Vector vec1 : ((EuclideanSpace) source).genericBaseToList()) {
			for (final Vector vec2 : ((EuclideanSpace) target).genericBaseToList()) {
				builder.append("<sourceVector>");
				builder.append(vec1.toXml());
				builder.append("</sourceVector>");

				builder.append("<targetVector>");
				builder.append(vec2.toXml());
				builder.append("</targetVector>");

				builder.append("<value>");
				builder.append(getImageVectorOfBaseVector(vec1).get(vec2).toXml());
				builder.append("</value>");
			}
		}
		builder.append("</base>");
		builder.append("</linearMapping>");
		return builder.toString();
	}

}
