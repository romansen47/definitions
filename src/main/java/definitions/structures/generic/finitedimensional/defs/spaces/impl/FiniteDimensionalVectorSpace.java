package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.CoordinateSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalVectorSpace implements CoordinateSpace {

	protected List<Vector> base;

	protected int dim;

	protected FiniteDimensionalVectorSpace() throws Throwable {
	}

	public FiniteDimensionalVectorSpace(List<Vector> genericBase) throws Throwable {
		dim = genericBase.size();
		base = genericBase;
	}

	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
		if (!(vec1 instanceof Tuple && vec2 instanceof Tuple)) {
			throw new Throwable();
		}
		double prod = 0;
		final Map<Vector, Double> vecCoord1 = ((Tuple) vec1).getCoordinates();
		final Map<Vector, Double> vecCoord2 = ((Tuple) vec2).getCoordinates();
		final List<Vector> base = genericBaseToList();
		for (final Vector vec : base) {
			prod += vecCoord1.get(vec) * vecCoord2.get(vec);
		}
		return prod;
	}

	@Override
	public boolean contains(Vector vec) throws Throwable {
		return (vec instanceof Tuple && vec.getDim() == dim());
	}

	@Override
	public FiniteVector nullVec() throws Throwable {
		Map<Vector, Double> coordinates = new HashMap<>();
		for (Vector vec : genericBaseToList()) {
			coordinates.put(vec, 0.);
		}
		return new Tuple(coordinates);
	}

	@Override
	public List<Vector> genericBaseToList() throws Throwable {
		return getBase();
	}

	@Override
	public int dim() throws Throwable {
		return getDim();
	}

	@Override
	public Set<Vector> getGenericBase() throws Throwable {
		return new HashSet<>(genericBaseToList());
	}

	/**
	 * @return the base
	 */
	public List<Vector> getBase() {
		return base;
	}

	protected void setBase(List<Vector> newBase) {
		base = newBase;
	}

	/**
	 * @return the dim
	 */
	protected int getDim() {
		return dim;
	}

}
