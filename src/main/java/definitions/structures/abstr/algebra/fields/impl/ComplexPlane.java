package definitions.structures.abstr.algebra.fields.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.PrimeField;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.RealSpace;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.MappingGenerator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FiniteDimensionalVectorSpace;

/**
 *
 * @author ro
 *
 *         Implementation of the field of complex numbers as a singleton class.
 */
public class ComplexPlane extends FiniteDimensionalVectorSpace implements Field, RealSpace {

	private static final long serialVersionUID = -6528124823296735558L;

	static private EuclideanSpace instance;

	private static RealLine realLine;

	public static EuclideanSpace getInstance() {
		if (instance == null) {
			instance = new ComplexPlane();
		}
		return instance;
	}

	public static void setRealLine(final RealLine realLine) {
		ComplexPlane.realLine = realLine;
	}

	private final Complex zero;

	private final Complex one;

	private final Complex i;

	private Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = null;

	public ComplexPlane() {
		dim = 2;
		base = new ArrayList<>();
		one = new Complex(realLine.getOne(), realLine.getZero());
		zero = new Complex(realLine.getZero(), realLine.getZero());
		i = new Complex(realLine.getZero(), realLine.getOne());
		base.add(one);
		base.add(i);
	}

	@Override
	public Complex addition(final Vector vec1, final Vector vec2) {
		final Vector ans = super.addition(vec1, vec2);
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(one),
				((FiniteVectorMethods) ans).getCoordinates().get(i));
	}

	public Complex complex() {
		return new Complex(0, 0);
	}

	@Override
	public Complex conjugate(final Scalar value) {
		final Complex v = (Complex) value;
		return new Complex(v.getReal().getDoubleValue(), -v.getImag().getDoubleValue());
	}

	@Override
	public boolean contains(final Vector vec) {
		return (vec instanceof Complex) || (vec == zero) || (vec == one) || (vec == null);
	}

	@Override
	public Complex get(final double realValue) {
		final Complex newComplex = complex();
		newComplex.setValue(realValue, 0);
		return newComplex;
	}

	public Complex get(final double realValue, final double imValue) {
		final Complex newComplex = complex();
		newComplex.setValue(realValue, imValue);
		return newComplex;
	}

	public Complex getI() {
		return i;
	}

	@Override
	public Map<Vector, VectorSpaceHomomorphism> getMultiplicationMatrix() {

		if (multiplicationMatrix == null) {

			final Scalar realOne = RealLine.getInstance().getOne();
			final Scalar realZero = RealLine.getInstance().getZero();
			final Scalar neg = RealLine.getInstance().get(-1);

			final Scalar[][] oneMat = new Scalar[][] { { realOne, realZero }, { realZero, realOne } };
			final VectorSpaceHomomorphism oneHom = MappingGenerator.getInstance()
					.getFiniteDimensionalLinearMapping(this, this, oneMat);

			final Scalar[][] iMat = new Scalar[][] { { realZero, neg }, { realOne, realZero } };
			final VectorSpaceHomomorphism iHom = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(this,
					this, iMat);

			final Map<Vector, VectorSpaceHomomorphism> newMap = new HashMap<>();

			newMap.put(one, oneHom);
			newMap.put(i, iHom);

			setMultiplicationMatrix(newMap);
		}
		return multiplicationMatrix;
	}

	@Override
	public Complex getOne() {
		return one;
	}

	@Override

	public Complex getMultiplicativeInverseElement(final Vector factor) {
		return (Complex) Field.super.getMultiplicativeInverseElement(factor);
	}

	@Override
	public Complex nullVec() {
		return zero;
	}

	@Override
	public Complex product(final Vector vec1, final Vector vec2) {
		return (Complex) Field.super.product(vec1, vec2);
	}

	/**
	 * @param multiplicationMatrix the multiplicationMatrix to set
	 */
	@Override
	public void setMultiplicationMatrix(final Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix) {
		this.multiplicationMatrix = multiplicationMatrix;
	}

	@Override
	public Complex stretch(final Vector vec1, final Scalar r) {
		final Vector ans = Field.super.stretch(vec1, r);
		return new Complex(((FiniteVectorMethods) ans).getCoordinates().get(one),
				((FiniteVectorMethods) ans).getCoordinates().get(i));

	}

//	@Override
//	public String toString() {
//		return "the field of complex numbers, which is modelled as a 2-dimensional real vector space.";
//	}

	@Override
	public String toXml() {
		return "<ComplexPlane />\r";
	}

	@Override
	public Complex getMinusOne() {
		return new Complex(-1., 0.);
	}

	@Override
	public Complex getNeutralElement() {
		return (Complex) getZero();
	}

	@Override
	public PrimeField getPrimeField() {
		return Generator.getInstance().getGroupGenerator().getRationals();
	}

}
