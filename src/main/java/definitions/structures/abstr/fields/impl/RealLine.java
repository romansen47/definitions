package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.mappings.impl.RealOne;
import definitions.structures.abstr.mappings.impl.RealZero;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalInjection;
import definitions.structures.euclidean.mappings.impl.InjectiveLinearMapping;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.SubField;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

public class RealLine implements SubField, RealSpace {

	private EuclideanSpace dualSpace;

	final private static Real one = RealOne.getOne();
	final private static Real zero = RealZero.getZero();

	private static RealLine instance;

	private final List<Vector> base;

	private Map<Vector, Homomorphism> multiplicationMatrix;

	private RealLine() {
		this.base = new ArrayList<>();
		this.base.add(this.getOne());
		final Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
		final Map<Vector, Scalar> a = new HashMap<>();
		a.put(RealLine.one, RealLine.one);
		multiplicationMap.put(RealLine.one, a);
		final Map<Vector, Homomorphism> newMap = new HashMap<>();
		newMap.put(one,
				MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));
		this.setMultiplicationMatrix(newMap);

	}

	public static RealLine getInstance() {
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
		return this.getZero();
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
		return new Real(((Real) vec1).getValue() * ((Real) vec2).getValue());
	}

	@Override
	public Vector projection(Vector w, Vector v) {
		final Real a = ((Real) v);
		final Real b = ((Real) w);
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
	public Integer getDim() {
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

	@Override
	public Vector product(Vector vec1, Vector vec2) {
		final double val1 = ((Real) vec1).getValue();
		final double val2 = ((Real) vec2).getValue();
		final double ans = val1 * val2;
		return new Real(ans);
	}

	@Override
	public Vector inverse(Vector factor) {
		if (factor == null) {
			return null;
		}
		final Real num = ((Real) factor);
		if (num.getValue() == 0.0) {
			return null;
		}
		return new Real(1 / num.getValue());
	}

	@Override
	public final Real getOne() {
		return one;
	}

	@Override
	public final Real getZero() {
		return zero;
	}

	@Override
	public Field getField() {
		return RealSpace.super.getField();
	}

	@Override
	public EuclideanSpace getSuperSpace() {
		return ComplexPlane.getInstance();
	}

	@Override
	public FiniteDimensionalInjection getEmbedding() {
		final Map<Vector, Map<Vector, Scalar>> coord = new HashMap<>();
		final Map<Vector, Scalar> tmp = new HashMap<>();
		tmp.put(((Field) this.getSuperSpace()).getOne(), this.getOne());
		tmp.put(((ComplexPlane) this.getSuperSpace()).getI(), this.getZero());
		coord.put(this.getOne(), tmp);
		return new InjectiveLinearMapping(this, ComplexPlane.getInstance(), coord);
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

}