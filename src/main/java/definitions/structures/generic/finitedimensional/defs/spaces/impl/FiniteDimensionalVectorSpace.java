package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalVectorSpace implements EuclideanSpace {

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
		final Map<Vector, Double> vecCoord1 = vec1.getCoordinates();
		final Map<Vector, Double> vecCoord2 = vec2.getCoordinates();
		final List<Vector> base = genericBaseToList();
		for (final Vector vec : base) {
			prod += vecCoord1.get(getBaseVec(vec)) * vecCoord2.get(getBaseVec(vec));
		}
		return prod;
	}

	@Override
	public boolean contains(Vector vec) throws Throwable {
		return (vec instanceof Tuple && vec.getDim() == dim());
	}

	@Override
	public Vector nullVec() throws Throwable {
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

	@Override
	public Vector getCoordinates(Vector vec) throws Throwable {
		Map<Vector, Double> coordinates = new HashMap<>();
		for (Vector baseVec : getGenericBase()) {
			coordinates.put(baseVec, product(vec, baseVec));
		}
		return get(coordinates);
	}
	
	@Override
	public double getDistance(Vector ans, Vector vec2) throws Throwable {
		Vector diff = add(ans, (stretch(vec2, -1)));
		return norm(diff);
	}
	
	@Override
	public List<Vector> getOrthonormalBase(List<Vector> base) throws Throwable{
		List<Vector> newBase=new ArrayList<>();
		for (Vector vec:base) {
			Vector tmp=nullVec();
			for (Vector vec2:newBase) {
				tmp=add(tmp,normedProjection(vec,vec2));
			}
			Vector ans=normalize(add(vec,stretch(tmp,-1)));
			newBase.add(ans);
		}
		return newBase;
	}

	@Override
	public Vector normedProjection(Vector w, Vector v) throws Throwable {
		return stretch(v,product(w,v));///Math.pow(norm(v), 2));
	}
}
