package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.groups.GroupElement;
import definitions.structures.abstr.groups.impl.FiniteCyclicRing;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

@Component(value = "binaryField")
public final class BinaryField extends FiniteCyclicRing implements PrimeField {

	private static final long serialVersionUID = -7935335390082721765L;

	private static EuclideanSpace instance;

	private Map<Vector, Homomorphism> multiplicationMatrix;

	private final int characteristic = 2;

	private List<Vector> base;

	private EuclideanSpace dualSpace;
 
	private BinaryField() {
		super(2);
		base=new ArrayList<>();
		base.add(new Bit(0));
		base.add(new Bit(1));
		elements.put(0,base.get(0));
		elements.put(1,base.get(1)); 
	}

	@Override
	public Field getField() {
		return this;
	}

	public static EuclideanSpace getInstance() {
		if (instance==null) {
			instance = new BinaryField();
		}
		return instance;
	}

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		return this.get(vec1.equals(this.getOne()) && vec2.equals(this.getOne()));
	}

	public Vector get(Boolean val) {
		if (val) {
			return (Vector) this.getGenerator();
		}
		return (Vector) this.getIdentityElement();
	}

	@Override
	public boolean contains(Vector vec) {
		return vec == (Vector) this.getGenerator() || vec==this.getIdentityElement();
	}

	@Override
	public Vector nullVec() {
		return (Vector) this.getIdentityElement();
	}

	@Override
	public Vector add(Vector vec1, Vector vec2) {
		return this.get(!vec1.equals(vec2));
	}

	@Override
	public Vector stretch(Vector vec1, Scalar r) {
		return this.get(vec1.equals((Vector) this.getGenerator()) && r.equals((Vector) this.getGenerator()));
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
	
	class Bit extends Element implements Scalar,Vector{
		protected Bit(int r) {
			super(r);
		}

		@Override
		public double getValue() { 
			return getRepresentant();
		}

		@Override
		public Scalar getInverse() {
			if (getRepresentant()==0) {
				return null;
			}
			return get(1);
		}
	}
}
