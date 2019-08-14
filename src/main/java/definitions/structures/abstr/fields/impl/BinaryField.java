package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.False;
import definitions.structures.abstr.fields.scalars.impl.True;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

public final class BinaryField implements FiniteField {

	private static EuclideanSpace instance;

	private EuclideanSpace dualSpace;

	final Vector zero = False.getInstance();

	final Vector unit = True.getInstance();

	private final List<Vector> base = new ArrayList<>();

	private Map<Vector, Homomorphism> multiplicationMatrix;

	private BinaryField() {
//		this.base.add(this.zero);
		this.base.add(this.unit);
		final Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
		final Map<Vector, Scalar> a = new HashMap<>();
		a.put(this.unit, (Scalar) this.unit);
		multiplicationMap.put(this.unit, a);
		final Map<Vector, Homomorphism> newMap = new HashMap<>();
		newMap.put(this.unit,
				MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));
		this.setMultiplicationMatrix(newMap);
	}

	@Override
	public Field getField() {
		return this;
	}

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new BinaryField();
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
		if (v == False.getInstance()) {
			return False.getInstance();
		}
		return w;
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
	public Vector getCoordinates(Vector vec) {
		return vec;
	}

	@Override
	public List<Vector> getOrthonormalBase(List<Vector> base) {
		return base;
	}

	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {
		return this.multiplicationMatrix;
	}

	@Override
	public void setMultiplicationMatrix(Map<Vector, Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;

	}

	@Override
	public EuclideanSpace getDualSpace() {
		if (this.dualSpace == null) {
			this.dualSpace = new FunctionalSpace(this);
		}
		return this.dualSpace;
	}

	@Override
	public Integer getOrder() {
		return 2;
	}

	@Override
	public int getCharacteristic() {
		return 2;
	}

	@Override
	public FiniteField getPrimeField() {
		return (FiniteField) instance;
	}

}
