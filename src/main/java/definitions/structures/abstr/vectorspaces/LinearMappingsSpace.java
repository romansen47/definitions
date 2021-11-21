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
		source.genericBaseToList().stream().forEach(vec -> {
			coordinates.put(vec, ((FiniteVectorMethods) target.addition(((VectorSpaceHomomorphism) vec1).get(vec),
					((VectorSpaceHomomorphism) vec2).get(vec))).getCoordinates());
		});
		if ((vec1 instanceof VectorSpaceEndomorphism) && (vec1 instanceof VectorSpaceEndomorphism)) {
			return new LinearSelfMapping(source, coordinates);
		}
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	/**
	 * stretching linear mappings.
	 */
	@Override
	public Vector stretch(final Vector vec1, final Scalar r) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new HashMap<>();
		source.genericBaseToList().stream().forEach(vec -> {
			coordinates.put(vec, ((FiniteVectorMethods) target.stretch(((VectorSpaceHomomorphism) vec1).get(vec), r))
					.getCoordinates());
		});
		return new FiniteDimensionalLinearMapping(source, target, coordinates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		String ans = "<linearMappingSpace>";
		ans += "<source>" + source.toXml() + "</source>";
		ans += "<target>" + target.toXml() + "</target>";
		ans += "</linearMappingSpace>";
		return ans;
	}

}
