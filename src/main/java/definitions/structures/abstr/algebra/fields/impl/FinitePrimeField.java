package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.groups.GroupElement;
import definitions.structures.abstr.algebra.monoids.MonoidElement;
import definitions.structures.abstr.algebra.rings.impl.FiniteResidueClassRing;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
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

	private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix;

	private final int characteristic;

	private List<Vector> base;

	private EuclideanSpace dualSpace;

	public FinitePrimeField(final int prime) {
		super(prime);
		this.characteristic = prime;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector add(final Vector vec1, final Vector vec2) {
		return this.operation(vec1, vec2);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Scalar conjugate(final Scalar value) {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean contains(final Vector vec) {
		return vec == (Vector) this.getGenerator() || vec == this.getNeutralElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createElements(final int n) {
		this.base = new ArrayList<>();
		for (int i = 0; i < n; i++) {
			final PrimeFieldElement element = new PrimeFieldElement(i, this);
			this.getElements().put(i, element);
		}
		this.base.add((Vector) this.getElements().get(1));
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector getCoordinates(final Vector vec) {
		return vec;
	}

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field getField() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeFieldElement getInverseElement(final GroupElement element) {
		return (PrimeFieldElement) super.getInverseElement(element);
	}

	/**
	 * Getter for multiplication matrix.
	 */
	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
		final Scalar one = this.getOne();
		if (this.multiplicationMatrix == null) {
			this.multiplicationMatrix = new HashMap<>();
			final VectorSpaceHomomorphism identity = new LinearMapping(this, this) {

				private static final long serialVersionUID = 1L;

				@Override
				public MonoidElement get(final MonoidElement vec) {
					return vec;
				}

				@Override
				public Scalar[][] getGenericMatrix() {
					final Scalar[][] mat = new Scalar[1][1];
					mat[0][0] = FinitePrimeField.this.getOne();
					return mat;
				}

				@Override
				public Map<Vector, Map<Vector, Scalar>> getLinearity() {
					final Map<Vector, Map<Vector, Scalar>> map = new HashMap<>();
					final Map<Vector, Scalar> tmp = new HashMap<>();
					tmp.put(FinitePrimeField.this.getOne(), FinitePrimeField.this.getOne());
					map.put(FinitePrimeField.this.getOne(), tmp);
					return map;
				}
			};
			this.multiplicationMatrix.put(this.getOne(), identity);
		}
		return this.multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeFieldElement getOne() {
		return (PrimeFieldElement) this.getGenerator();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		return base;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeField getPrimeField() {
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeFieldElement nullVec() {
		return (PrimeFieldElement) this.getNeutralElement();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PrimeFieldElement operation(final MonoidElement first, final MonoidElement second) {
		return (PrimeFieldElement) super.operation(first, second);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector projection(final Vector w, final Vector v) {
		if (v == (Vector) this.getNeutralElement()) {
			return (Vector) this.getNeutralElement();
		}
		return w;
	}

	/**
	 * Setter for multiplication matrix.
	 */
	@Override
	public void setMultiplicationMatrix(final Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}
}
