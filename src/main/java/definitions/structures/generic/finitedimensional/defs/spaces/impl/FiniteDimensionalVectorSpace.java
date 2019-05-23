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

	protected List<FiniteVector> base;

	protected int dim;

	protected FiniteDimensionalVectorSpace() throws Throwable {
	}

	public FiniteDimensionalVectorSpace(List<FiniteVector> list) throws Throwable {
		dim = list.size();
		base = list;
	}

	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
		if (!(vec1 instanceof Tuple && vec2 instanceof Tuple)) {
			throw new Throwable();
		}
		double prod = 0;
		final Map<FiniteVector, Double> vecCoord1 = ((Tuple) vec1).getCoordinates();
		final Map<FiniteVector, Double> vecCoord2 = ((Tuple) vec2).getCoordinates();
		final List<FiniteVector> base = genericBaseToList();
		for (final FiniteVector vec : base) {
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
		Map<FiniteVector, Double> coordinates = new HashMap<>();
		for (FiniteVector vec : genericBaseToList()) {
			coordinates.put(vec, 0.);
		}
		return new Tuple(coordinates);
	}

	@Override
	public List<FiniteVector> genericBaseToList() throws Throwable {
		return getBase();
	}

	@Override
	public int dim() throws Throwable {
		return getDim();
	}

	@Override
	public Set<FiniteVector> getGenericBase() throws Throwable {
		return new HashSet<>(genericBaseToList());
	}

	/**
	 * @return the base
	 */
	public List<FiniteVector> getBase() {
		return base;
	}

	protected void setBase(List<FiniteVector> newBase) {
		base = newBase;
	}

	/**
	 * @return the dim
	 */
	protected int getDim() {
		return dim;
	}

}
