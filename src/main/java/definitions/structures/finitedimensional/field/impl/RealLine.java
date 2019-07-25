package definitions.structures.finitedimensional.field.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import definitions.structures.abstr.Algebra;
import definitions.structures.abstr.Field;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.impl.RealOne;
import definitions.structures.abstr.impl.RealZero;
import definitions.structures.finitedimensional.real.vectors.Real;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public final class RealLine implements Field, EuclideanSpace{

	final private static Real one = RealOne.getOne();
	final private static Real zero = RealZero.getZero();

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
		return getZero();
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		return new Real(((Real) vec1).getValue() + ((Real) vec2).getValue());
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		return new Real(((Real) vec1).getValue() * r.getValue());
	}

	@Override
	public Real innerProduct(Vector vec1, Vector vec2) {
		return new Real( ((Real) vec1).getValue() * ((Real) vec2).getValue());
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		Real a = ((Real) v);
		Real b = ((Real) w);
		if (b.getValue() != 0) {
			return this.stretch(w, new Real(a.getValue() / b.getValue()));
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
	public Integer dim() {
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
	
	public final Real getZero() {
		return zero;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		double val1=((Real)vec1).getValue();
		double val2=((Real)vec2).getValue();
		double ans=val1*val2;
		return new Real(ans);
	}

	@Override
	public Vector inverse(Vector factor) {
		if (factor==null) {
			return null;
		}
		Real num = ((Real)factor);
		if (num.getValue()==0.0){
			return null;
		}
		return new Real(1/num.getValue());
	}

}
