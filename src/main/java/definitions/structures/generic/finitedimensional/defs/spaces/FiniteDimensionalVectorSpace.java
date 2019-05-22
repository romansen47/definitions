package definitions.structures.generic.finitedimensional.defs.spaces;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FiniteDimensionalVectorSpace implements IFiniteDimensionalVectorSpace {

	protected List<IFiniteVector> base;

	protected int dim;

	protected FiniteDimensionalVectorSpace() throws Throwable {
	}
	
	public FiniteDimensionalVectorSpace(List<IFiniteVector> list) throws Throwable {
		dim = list.size();
		base = list;
	}

	@Override
	public double product(Vector vec1, Vector vec2) throws Throwable {
		if (!(vec1 instanceof FiniteVector && vec2 instanceof FiniteVector)) {
			throw new Throwable();
		}
		double prod = 0;
		final Map<IFiniteVector, Double> vecCoord1 = ((FiniteVector) vec1).getCoordinates();
		final Map<IFiniteVector, Double> vecCoord2 = ((FiniteVector) vec2).getCoordinates();
		final List<IFiniteVector> base = genericBaseToList();
		for (final IFiniteVector vec : base) {
			prod += vecCoord1.get(vec) * vecCoord2.get(vec);
		}
		return prod;
	}

	@Override
	public boolean contains(Vector vec) throws Throwable {
		return (vec instanceof FiniteVector && vec.getDim() == dim());
	}

	@Override
	public IFiniteVector nullVec() throws Throwable {
		Map<IFiniteVector, Double> coordinates = new HashMap<>();
		for (IFiniteVector vec : genericBaseToList()) {
			coordinates.put(vec, 0.);
		}
		return new FiniteVector(coordinates);
	}

	@Override
	public List<IFiniteVector> genericBaseToList() throws Throwable {
		return getBase();
	}

	@Override
	public int dim() throws Throwable {
		return getDim();
	}

	@Override
	public Set<IFiniteVector> getGenericBase() throws Throwable {
		return new HashSet<>(genericBaseToList());
	}

	/**
	 * @return the base
	 */
	public List<IFiniteVector> getBase() {
		return base;
	}

	protected void setBase(List<IFiniteVector> newBase) {
		base = newBase;
	}

	/**
	 * @return the dim
	 */
	protected int getDim() {
		return dim;
	}

}
