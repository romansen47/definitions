package definitions.structures.abstr.vectorspaces;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.VectorSpaceEndomorphism;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.mappings.impl.LinearSelfMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class LinearMappingsSpace implements VectorSpace, RealSpace {

	private static final long serialVersionUID = 2181150043690171777L;
	final EuclideanSpace source;
	final EuclideanSpace target;

	public LinearMappingsSpace(final EuclideanSpace source, final EuclideanSpace target) {
		this.source = source;
		this.target = target;
	}

	/**
	 * Addition of linear mappings.
	 */
	@Override
	public Vector addition(final Vector vec1, final Vector vec2) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new HashMap<>();
		for (final Vector vec : this.source.genericBaseToList()) {
			coordinates.put(vec, ((FiniteVectorMethods) this.target.addition(((VectorSpaceHomomorphism) vec1).get(vec),
					((VectorSpaceHomomorphism) vec2).get(vec))).getCoordinates());
		}
		if (vec1 instanceof VectorSpaceEndomorphism && vec1 instanceof VectorSpaceEndomorphism) {
			return new LinearSelfMapping(this.source, coordinates);
		}
		return new FiniteDimensionalLinearMapping(this.source, this.target, coordinates);
	}

	/**
	 * stretching linear mappings.
	 */
	@Override
	public Vector stretch(final Vector vec1, final Scalar r) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new HashMap<>();
		for (final Vector vec : this.source.genericBaseToList()) {
			coordinates.put(vec,
					((FiniteVectorMethods) this.target.stretch(((VectorSpaceHomomorphism) vec1).get(vec), r))
							.getCoordinates());
		}
		return new FiniteDimensionalLinearMapping(this.source, this.target, coordinates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		String ans = "<linearMappingSpace>";
		ans += "<source>" + this.source.toXml() + "</source>";
		ans += "<target>" + this.target.toXml() + "</target>";
		ans += "</linearMappingSpace>";
		return ans;
	}

}
