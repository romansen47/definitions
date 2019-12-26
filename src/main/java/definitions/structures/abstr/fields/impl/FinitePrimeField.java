package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.MonoidElement;
import definitions.structures.abstr.groups.impl.FiniteResidueClassRing;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.mappings.impl.LinearMapping;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

public class FinitePrimeField extends FiniteResidueClassRing implements FiniteField, PrimeField {

	private static final long serialVersionUID = -7935335390082721765L;

	private static Map<Integer, EuclideanSpace> instances = new HashMap<>();

	public static EuclideanSpace getInstance(final int prime) {
		EuclideanSpace instance = instances.get(prime);
		if (instance == null) {
			instance = new FinitePrimeField(prime);
			instances.put(prime, instance);
		}
		return instance;
	}

	private Map<Vector, Homomorphism> multiplicationMatrix;

	private final int characteristic;

	private List<Vector> base;

	private EuclideanSpace dualSpace;

	public FinitePrimeField(final int prime) {
		super(prime);
		this.characteristic = prime;
	}

	@Override
	public Vector add(final Vector vec1, final Vector vec2) {
		return (Vector) this.operation(vec1, vec2);
	}

	/**
	 * @return the conjugated
	 */
	@Override
	public Scalar conjugate(final Scalar value) {
		return value;
	}

	@Override
	public boolean contains(final Vector vec) {
		return vec == (Vector) this.getGenerator() || vec == this.getIdentityElement();
	}

	@Override
	protected void createElements(final int n) {
		this.base = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			final FieldElement element = new PrimeFieldElement(i, this);
			this.elements.put(i, element);
		}
		this.base.add((Vector) this.elements.get(1));
	}

	@Override
	public List<Vector> genericBaseToList() {
		return this.base;
	}

	/**
	 * @return the characteristic
	 */
	@Override
	public int getCharacteristic() {
		return this.characteristic;
	}

	@Override
	public Vector getCoordinates(final Vector vec) {
		return vec;
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	/**
	 * @return the dual space.
	 * 
	 * @TODO: Should be isomorphic to this... should be tested.
	 */
	@Override
	public EuclideanSpace getDualSpace() {
		if (this.dualSpace == null) {
			this.dualSpace = new FunctionalSpace(this);
		}
		return this.dualSpace;
	}

	@Override
	public Field getField() {
		return this;
	}

	@Override
	public PrimeFieldElement getInverseElement(final GroupElement element) {
		return (PrimeFieldElement) super.getInverseElement(element);
	}

	/**
	 * Getter for multiplication matrix.
	 */
	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {
		final Scalar one = (Scalar) this.getOne();
		if (this.multiplicationMatrix == null) {
			this.multiplicationMatrix = new HashMap<>();
			final Homomorphism identity = new LinearMapping(this, this) {

				private static final long serialVersionUID = 1L;

				@Override
				public Vector get(final Vector vec) {
					return vec;
				}

				@Override
				public Scalar[][] getGenericMatrix() {
					final Scalar[][] mat = new Scalar[1][1];
					mat[0][0] = (Scalar) FinitePrimeField.this.getOne();
					return mat;
				}

				@Override
				public Map<Vector, Map<Vector, Scalar>> getLinearity() {
					final Map<Vector, Map<Vector, Scalar>> map = new HashMap<>();
					final Map<Vector, Scalar> tmp = new HashMap<>();
					tmp.put(FinitePrimeField.this.getOne(), (Scalar) FinitePrimeField.this.getOne());
					map.put(FinitePrimeField.this.getOne(), tmp);
					return map;
				}
			};
			this.multiplicationMatrix.put(this.getOne(), identity);
		}
		return this.multiplicationMatrix;
	}

	@Override
	public Vector getOne() {
		return (Vector) this.getGenerator();
	}

	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		return base;
	}

	@Override
	public PrimeField getPrimeField() {
		return this;
	}

	@Override
	public Vector nullVec() {
		return (Vector) this.getIdentityElement();
	}

	@Override
	public FieldElement operation(final MonoidElement first, final MonoidElement second) {
		return (FieldElement) super.operation(first, second);
	}

	@Override
	public Vector projection(final Vector w, final Vector v) {
		if (v == (Vector) this.getIdentityElement()) {
			return (Vector) this.getIdentityElement();
		}
		return w;
	}

	/**
	 * Setter for multiplication matrix.
	 */
	@Override
	public void setMultiplicationMatrix(final Map<Vector, Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	@Override
	public String toString() {
		return "the binary field";
	}

}
