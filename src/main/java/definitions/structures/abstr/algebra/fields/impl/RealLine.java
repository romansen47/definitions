package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.FieldElement;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.algebra.fields.scalars.impl.RealOne;
import definitions.structures.abstr.algebra.fields.scalars.impl.RealZero;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;
import settings.GlobalSettings;

/**
 *
 * @author ro
 *
 *         Implementation of the field of real numbers as a singleton class.
 */
@Component
public class RealLine implements Field, RealSpace {

	final private static Real one = RealOne.getOne();
	final private static Real zero = RealZero.getZero();
	private static RealLine instance;

	public static RealLine getInstance() {
		return RealLine.instance;
	}

	public static void setInstance(final RealLine realLine) {
		RealLine.instance = realLine;
	}

	private EuclideanSpace dualSpace;
	final Map<Vector, Scalar> coordinates;

	private final List<Vector> base;

	private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix;

	public RealLine() {
		base = new ArrayList<>();
		base.add(getOne());
		final Map<Vector, Map<Vector, Scalar>> multiplicationMap = new HashMap<>();
		final Map<Vector, Scalar> a = new HashMap<>();
		a.put(RealLine.one, RealLine.one);
		coordinates = a;
		multiplicationMap.put(RealLine.one, a);
		final Map<Vector, VectorSpaceHomomorphism> newMap = new HashMap<>();
		newMap.put(RealLine.one,
				MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this, this, multiplicationMap));
		setMultiplicationMatrix(newMap);
		ComplexPlane.setRealLine(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Real addition(final Vector vec1, final Vector vec2) {
		return this.get(((Real) vec1).getDoubleValue() + ((Real) vec2).getDoubleValue());
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
		return vec instanceof Real;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Vector> genericBaseToList() {
		return base;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Real get(final double value) {
		if (Math.abs(value) < GlobalSettings.REAL_EQUALITY_FEINHEIT) {
			return getZero();
		}
		final Real newReal = new Real();
		newReal.setRepresentant(value);
		return newReal;
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
	 * {@inheritDoc}
	 */
	@Override
	public EuclideanSpace getDualSpace() {
		if (dualSpace == null) {
			dualSpace = new FunctionalSpace(this);
		}
		return dualSpace;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field getField() {
		return RealSpace.super.getField();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {
		return multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Real getOne() {
		return RealLine.one;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Vector> getOrthonormalBase(final List<Vector> base) {
		return this.base;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final Real getZero() {
		return RealLine.zero;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Real innerProduct(final Vector vec1, final Vector vec2) {
		return this.get(((Real) vec1).getDoubleValue() * ((Real) vec2).getDoubleValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Real getMultiplicativeInverseElement(final Vector factor) {
		if (factor == null) {
			return null;
		}
		final Real num = ((Real) factor);
		if (num.getDoubleValue() == 0.0) {
			return null;
		}
		return this.get(1 / num.getDoubleValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isIrreducible(final Element element) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector nullVec() {
		return getZero();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector product(final Vector vec1, final Vector vec2) {
		final double val1 = ((Real) vec1).getDoubleValue();
		final double val2 = ((Real) vec2).getDoubleValue();
		final double ans = val1 * val2;
		return this.get(ans);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Vector projection(final Vector w, final Vector v) {
		final Real a = ((Real) v);
		final Real b = ((Real) w);
		if (b.getDoubleValue() != 0) {
			return stretch(w, this.get(a.getDoubleValue() / b.getDoubleValue()));
		}
		return nullVec();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setMultiplicationMatrix(final Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Real stretch(final Vector vec1, final Scalar r) {
		return this.get(((Real) vec1).getDoubleValue() * ((Real) r).getDoubleValue());
	}

	// /**
	// * {@inheritDoc}
	// */
	// @Override
	// public String toString() {
	// return "the field of real numbers";
	// }

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toXml() {
		return "<realline />";
	}

	@Override
	public Real getNeutralElement() {
		return this.get(0.);
	}

	@Override
	public FieldElement getInverseElement(Element element) {
		return this.get(-((Real) element).getDoubleValue());
	}

	@Override
	public Element getMinusOne() {
		return this.get(-1);
	}

	@Override
	public PrimeField getPrimeField() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

}
