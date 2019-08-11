package definitions.structures.field.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.EuclideanField;
import definitions.structures.field.Field;
import definitions.structures.field.scalar.impl.False;
import definitions.structures.field.scalar.impl.True;
import definitions.structures.finitedimensional.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;

public final class Modulo2 implements EuclideanSpace, EuclideanField {

	private static EuclideanSpace instance;

	final Vector zero = False.getInstance();

	final Vector unit = True.getInstance();

	private final List<Vector> base = new ArrayList<>();

	private Homomorphism multiplicationMatrix;

	private Modulo2() {
//		this.base.add(this.zero);
		this.base.add(this.unit);
		Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
		Map<Vector, Scalar> a = new HashMap<>();
		a.put(this.unit, (Scalar) this.unit);
		multiplicationMap.put(this.unit, a);
		this.setMultiplicationMatrix(
				MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));
	}

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new Modulo2();
		}
		return instance;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		return this.get(vec1.equals(vec2));
	}

	public Vector get(Boolean val) {
		if (val) {
			return this.unit;
		}
		return this.zero;
	}

	@Override
	public Field getField() {
		return this;
	}

	@Override
	public boolean contains(Vector vec) {
		return vec instanceof True || vec instanceof False;
	}

	@Override
	public Vector nullVec() {
		return this.zero;
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		return this.get(!vec1.equals(vec2));
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		return this.get(vec1.equals(this.unit) && r.equals(this.unit));
	}

	@Override
	public Vector inverse(Vector factor) {
		if (factor instanceof True) {
			return this.unit;
		}
		return null;
	}

	@Override
	public Vector getOne() {
		return this.unit;
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vector> genericBaseToList() {
		// TODO Auto-generated method stub
		return this.base;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Vector> getGenericBase() {
		return (Set<Vector>) this.base;
	}

	@Override
	public Vector getCoordinates(Vector vec) {
		return vec;
	}

	@Override
	public List<Vector> getOrthonormalBase(List<Vector> base) {
		return base;
	}

	@Override
	public Homomorphism getMultiplicationMatrix() {
		return this.multiplicationMatrix;
	}

	@Override
	public void setMultiplicationMatrix(Homomorphism multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;

	}

}
