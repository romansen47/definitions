package definitions.structures.abstr.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.fields.scalars.impl.RealOne;
import definitions.structures.abstr.fields.scalars.impl.RealZero;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.RingElement;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.FiniteDimensionalInjection;
import definitions.structures.euclidean.mappings.impl.InjectiveLinearMapping;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.SubField;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;

/**
 * 
 * @author ro
 *
 *         Implementation of the field of real numbers as a singleton class.
 */
@Configuration
public class RealLine implements SubField, RealSpace {

	private static final long serialVersionUID = -1444063003774915383L;

	final private static Real one = RealOne.getOne();

	final private static Real zero = RealZero.getZero();

	private static RealLine instance;

	public static RealLine getInstance() {
		return instance;
	}

	public static void setInstance(final RealLine realLine) {
		instance = realLine;
	}

	private EuclideanSpace dualSpace;

	final Map<Vector, Scalar> coordinates;

	private final List<Vector> base;

	private Map<Vector, Homomorphism> multiplicationMatrix;

	public RealLine() {
		this.base = new ArrayList<>();
		this.base.add(this.getOne());
		final Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
		final Map<Vector, Scalar> a = new HashMap<>();
		a.put(RealLine.one, RealLine.one);
		this.coordinates = a;
		multiplicationMap.put(RealLine.one, a);
		final Map<Vector, Homomorphism> newMap = new HashMap<>();
		newMap.put(one,
				MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));
		this.setMultiplicationMatrix(newMap);
		ComplexPlane.setRealLine(this);
	}

	@Override
	public Vector add(final Vector vec1, final Vector vec2) {
		return this.get(((Real) vec1).getValue() + ((Real) vec2).getValue());
	}

	@Override
	public Scalar conjugate(final Scalar value) {
		return value;
	}

	@Override
	public boolean contains(final Vector vec) {
		return vec instanceof Real;
	}

	@Override
	public List<Vector> genericBaseToList() {
		return this.base;
	}

	@Override
	public Scalar get(final double value) {
		final Real newReal = new Real();// SpringConfiguration.getSpringConfiguration().real();
		newReal.setValue(value);
		return newReal;
	}

	@Override
	public Vector getCoordinates(final Vector vec) {
		return vec;
	}

	@Override
	public Integer getDim() {
		return 1;
	}

	@Override
	public EuclideanSpace getDualSpace() {
		if (this.dualSpace == null) {
			this.dualSpace = new FunctionalSpace(this);
		}
		return this.dualSpace;
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
	public Field getField() {
		return RealSpace.super.getField();
	}

	@Override
	public Map<Vector, Homomorphism> getMultiplicationMatrix() {
		return this.multiplicationMatrix;
	}

	@Override
	public final Real getOne() {
		return one;
	}

	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		return this.base;
	}

	@Override
	public EuclideanSpace getSuperSpace() {
		return ComplexPlane.getInstance();
	}

	@Override
	public final Real getZero() {
		return zero;
	}

	@Override
	public Real innerProduct(final Vector vec1, final Vector vec2) {
		return (Real) this.get(((Real) vec1).getValue() * ((Real) vec2).getValue());
	}

	@Override
	public Vector inverse(final Vector factor) {
		if (factor == null) {
			return null;
		}
		final Real num = ((Real) factor);
		if (num.getValue() == 0.0) {
			return null;
		}
		return this.get(1 / num.getValue());
	}

	@Override
	public boolean isIrreducible(final RingElement element) {
		return true;
	}

	@Override
	public Vector nullVec() {
		return this.getZero();
	}

	@Override
	public Vector product(final Vector vec1, final Vector vec2) {
		final double val1 = ((Real) vec1).getValue();
		final double val2 = ((Real) vec2).getValue();
		final double ans = val1 * val2;
		return this.get(ans);
	}

	@Override
	public Vector projection(final Vector w, final Vector v) {
		final Real a = ((Real) v);
		final Real b = ((Real) w);
		if (b.getValue() != 0) {
			return this.stretch(w, this.get(a.getValue() / b.getValue()));
		}
		return this.nullVec();
	}

	@Override
	public void setMultiplicationMatrix(final Map<Vector, Homomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	@Override
	public Vector stretch(final Vector vec1, final Scalar r) {
		return this.get(((Real) vec1).getValue() * r.getValue());
	}

	@Override
	public String toString() {
		return "the field of real numbers.";
	}

	@Override
	public String toXml() {
		return "<realNumbers/>";
	}

}
