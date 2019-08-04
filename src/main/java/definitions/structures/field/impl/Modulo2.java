package definitions.structures.field.impl;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.EuclideanField;
import definitions.structures.field.Field;
import definitions.structures.field.scalar.impl.False;
import definitions.structures.field.scalar.impl.True;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.FiniteDimensionalVectorSpace;

public final class Modulo2 extends FiniteDimensionalVectorSpace implements EuclideanField{
	
	private static EuclideanSpace instance;

	final Vector zero=False.getInstance();
	final Vector unit=True.getInstance();
	
	private Modulo2() {
		base.add(zero);
		base.add(unit);
	}
	
	public static EuclideanSpace getInstance() {
		if (instance==null) {
			instance=(EuclideanSpace) new Modulo2();
		}
		return instance;
	}
	
	
	@Override
	public Vector product(Vector vec1, Vector vec2) {
		return get(vec1.equals(vec2));
	}
	
	public Vector get(Boolean val) {
		if (val) {
			return unit;
		}
		return zero;
	}
	
	@Override
	public Field getField() {
		return (Field) this;
	}
	@Override
	public boolean contains(Vector vec) {
		return vec instanceof True || vec instanceof False;
	}
	@Override
	public Vector nullVec() {
		return zero;
	}
	@Override
	public Vector add(Vector vec1, Vector vec2) {
		return get(!vec1.equals(vec2));
	}
	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		return get(vec1.equals(unit) && r.equals(unit));
	}

	@Override
	public Vector inverse(Vector factor) {
		if (factor instanceof True) {
			return unit;
		}
		return null;
	}
	@Override
	public Vector getOne() {
		return unit;
	}
	
	@Override
	public Integer getDim() {
		return 1;
	}


}
