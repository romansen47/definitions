package definitions.structures.abstr.vectorspaces;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalLinearMapping;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class LinearMappingsSpace implements VectorSpace, RealSpace {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2181150043690171777L;
	final EuclideanSpace source;
	final EuclideanSpace target;

	public LinearMappingsSpace(EuclideanSpace source, EuclideanSpace target) {
		this.source = source;
		this.target = target;
	}

//	@Override
//	public boolean contains(Vector vec) {
//		return vec instanceof Homomorphism && vec.elementOf(this.source);
//	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new HashMap<>();
		for (final Vector vec : this.source.genericBaseToList()) {
			coordinates.put(vec, ((FiniteVectorMethods) this.target.add(((Homomorphism) vec1).get(vec),
					((Homomorphism) vec2).get(vec))).getCoordinates());
		}
		return new FiniteDimensionalLinearMapping(this.source, this.target, coordinates);
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		final Map<Vector, Map<Vector, Scalar>> coordinates = new HashMap<>();
		for (final Vector vec : this.source.genericBaseToList()) {
			coordinates.put(vec,
					((FiniteVectorMethods) this.target.stretch(((Homomorphism) vec1).get(vec), r)).getCoordinates());
		}
		return new FiniteDimensionalLinearMapping(this.source, this.target, coordinates);
	}

	@Override
	public String toXml() {
		String ans = "<linearMappingSpace>";
		ans += "<source>" + source.toXml() + "</source>";
		ans += "<target>" + target.toXml() + "</target>";
		ans += "</linearMappingSpace>";
		return ans;
	}

	@Override
	public Field getField() {
		// TODO Auto-generated method stub
		return null;
	}

}
