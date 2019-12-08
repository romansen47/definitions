package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.impl.FiniteCyclicRing;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

public class FinitePrimeField extends FiniteCyclicRing implements FiniteField, PrimeField {

	private static final long serialVersionUID = -7935335390082721765L;

	private static Map<Integer,EuclideanSpace> instances=new HashMap<>();

	private Map<Vector, Homomorphism> multiplicationMatrix;

	private final int characteristic;

	private List<Vector> base;

	private EuclideanSpace dualSpace;

	protected FinitePrimeField(int prime) {
		super(prime);
		characteristic=prime;
		base = new ArrayList<>();
		for (int i = 0; i < prime; i++) {
			PrimeFieldElement element=new PrimeFieldElement(i,this);
			base.add(element);
			elements.put(i, element);
		}
	}

	@Override
	public Field getField() {
		return this;
	}

	public static EuclideanSpace getInstance(int prime) {
		EuclideanSpace instance=instances.get(prime);
		if (instance == null) {
			instance = new FinitePrimeField(prime);
			instances.put(prime,instance);
		}
		return instance;
	}

	@Override
	public boolean contains(Vector vec) {
		return vec == (Vector) this.getGenerator() || vec == this.getIdentityElement();
	}

	@Override
	public Vector nullVec() {
		return (Vector) this.getIdentityElement();
	}

	@Override
	public Vector getOne() {
		return (Vector) this.getGenerator();
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		if (v == (Vector) this.getIdentityElement()) {
			return (Vector) this.getIdentityElement();
		}
		return w;
	}

	@Override
	public List<Vector> genericBaseToList() {
		return this.base;
	}

	@Override
	public Vector getCoordinates(Vector vec) {
		return vec;
	}

	@Override
	public List<Vector> getOrthonormalBase(List<Vector> base) {
		return base;
	}

	/**
	 * Getter for multiplication matrix.
	 */
	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {
		return this.multiplicationMatrix;
	}

	/**
	 * Setter for multiplication matrix.
	 */
	@Override
	public void setMultiplicationMatrix(Map<Vector, Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
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
	 * @return the order is two
	 */
	@Override
	public Integer getOrder() {
		return 2;
	}

	/**
	 * @return the conjugated
	 */
	@Override
	public Scalar conjugate(Scalar value) {
		return value;
	}

	/**
	 * @return the characteristic
	 */
	@Override
	public int getCharacteristic() {
		return this.characteristic;
	}

	@Override
	public String toString() {
		return "the binary field";
	}

	@Override
	public GroupElement getInverseElement(GroupElement element) {
		return super.getInverseElement(element);
	}

	@Override
	public GroupElement operation(GroupElement first, GroupElement second) {
		return (GroupElement) super.operation(first, second);
	}

	@Override
	public PrimeField getPrimeField() {
		return this;
	}

}
