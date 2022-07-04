package definitions.structures.abstr.vectorspaces;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.VectorSpaceEndomorphism;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.mappings.impl.LinearSelfMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public interface LinearMappingsSpace extends VectorSpace, RealSpace {

//	final EuclideanSpace source;
//	final EuclideanSpace target;

//	public LinearMappingsSpace(final EuclideanSpace source, final EuclideanSpace target) {
//		this.source = source;
//		this.target = target;
//	}

	/**
	 * Addition of linear mappings.
	 */
	@Override
	default Vector addition(final Vector vec1, final Vector vec2) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new ConcurrentHashMap<>();
		getSource().genericBaseToList().stream().forEach(vec -> {
			coordinates.put(vec, ((FiniteVectorMethods) getTarget().addition(((VectorSpaceHomomorphism) vec1).get(vec),
					((VectorSpaceHomomorphism) vec2).get(vec))).getCoordinates());
		});
		if ((vec1 instanceof VectorSpaceEndomorphism) && (vec1 instanceof VectorSpaceEndomorphism)) {
			return new LinearSelfMapping(getSource(), coordinates);
		}
		return new FiniteDimensionalLinearMapping(getSource(), getTarget(), coordinates);
	}

	/**
	 * stretching linear mappings.
	 */
	@Override
	public default Vector stretch(final Vector vec1, final Scalar r) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new ConcurrentHashMap<>();
		getSource().genericBaseToList().stream().forEach(vec -> {
			coordinates.put(vec,
					((FiniteVectorMethods) getTarget().stretch(((VectorSpaceHomomorphism) vec1).get(vec), r))
							.getCoordinates());
		});
		return new FiniteDimensionalLinearMapping(getSource(), getTarget(), coordinates);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public default String toXml() {
		String ans = "<linearMappingSpace>";
		ans += "<source>" + getSource().toXml() + "</source>";
		ans += "<target>" + getTarget().toXml() + "</target>";
		ans += "</linearMappingSpace>";
		return ans;
	}

	public EuclideanSpace getSource();

	public EuclideanSpace getTarget();

}
