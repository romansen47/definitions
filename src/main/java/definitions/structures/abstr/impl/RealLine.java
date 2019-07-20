package definitions.structures.abstr.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public final class RealLine implements EuclideanSpace {

	final private static Real one = RealOne.getOne();

	private static RealLine instance;
	private final List<Vector> base;

	private RealLine() {
		this.base = new ArrayList<>();
		this.base.add(this.getOne());
	}

	public static RealLine getRealLine() {
		if (instance == null) {
			instance = new RealLine();
		}
		return instance;
	}

	@Override
	public boolean contains(Vector vec) {
		return vec instanceof Real;
	}

	@Override
	public Vector nullVec() {
		return new Real(0);
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		return new Real(((Real) vec1).getValue() + ((Real) vec2).getValue());
	}

	@Override
	public Vector stretch(Vector vec1, double r) {
		return new Real(((Real) vec1).getValue() * r);
	}

	@Override
	public double product(Vector vec1, Vector vec2) {
		return ((Real) vec1).getValue() * ((Real) vec2).getValue();
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		Real a = ((Real) v);
		Real b = ((Real) w);
		if (b.getValue() != 0) {
			return this.stretch(w, a.getValue() / b.getValue());
		}
		return this.nullVec();
	}

	@Override
	public List<Vector> genericBaseToList() {
		return this.base;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Vector> getGenericBase() {
		return (Set<Vector>) this.base;
	}

	@Override
	public int dim() {
		return 1;
	}

	@Override
	public Vector getCoordinates(Vector vec) {
		return vec;
	}

	@Override
	public List<Vector> getOrthonormalBase(List<Vector> base) {
		return this.base;
	}

	public final Real getOne() {
		return one;
	}

}
