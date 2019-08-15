package definitions.structures.abstr.vectorspaces;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class LinearMappingsSpace implements VectorSpace, RealSpace {

	final EuclideanSpace source;
	final EuclideanSpace target;

	public LinearMappingsSpace(EuclideanSpace source, EuclideanSpace target) {
		this.source = source;
		this.target = target;
	}

	@Override
	public boolean contains(Vector vec) {
		return vec instanceof Homomorphism && vec.elementOf(source);
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new HashMap<>();
		for (Vector vec : source.genericBaseToList()) {
			coordinates.put(vec, (target.add(((Homomorphism) vec1).get(vec), ((Homomorphism) vec2).get(vec))).getCoordinates());
		}
		return new FiniteDimensionalLinearMapping(source,target,coordinates);
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new HashMap<>();
		for (Vector vec : source.genericBaseToList()) {
			coordinates.put(vec, (target.stretch(((Homomorphism) vec1).get(vec), r)).getCoordinates());
		}
		return new FiniteDimensionalLinearMapping(source,target,coordinates);
	}

}
