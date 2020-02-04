/**
 * 
 */
package definitions.structures.euclidean.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.ComplexPlane;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * @author BAU12350
 *
 */
public class FiniteDimensionalComplexFunctionSpaceTest {// extends FiniteDimensionalFunctionSpaceTest {

	public static void main(final String[] args) {
		new FiniteDimensionalComplexFunctionSpaceTest().test();
		System.exit(0);
	}

	final public Field f = (Field) ComplexPlane.getInstance();
	List<Vector> base = new ArrayList<>();

	FunctionSpace space;
	Function alpha;
	Function beta;

	Function gamma;

	@Before
	public void before() {
		this.alpha = new GenericFunction() {
			private static final long serialVersionUID = 6698998256903151087L;

			@Override
			public Field getField() {
				return FiniteDimensionalComplexFunctionSpaceTest.this.f;
			}

			@Override
			public String toString() {
				return "alpha";
			}

			@Override
			public Scalar value(final Scalar input) {
				final double val = ((Complex) input).getReal().getDoubleValue();
				final Complex tmp = (Complex) FiniteDimensionalComplexFunctionSpaceTest.this.f
						.addition(FiniteDimensionalComplexFunctionSpaceTest.this.f.getOne(), new Complex(val, -val));
				return (Scalar) FiniteDimensionalComplexFunctionSpaceTest.this.f.normalize(tmp);
			}
		};

		this.beta = new GenericFunction() {
			private static final long serialVersionUID = -2624612868740391242L;

			@Override
			public Field getField() {
				return FiniteDimensionalComplexFunctionSpaceTest.this.f;
			}

			@Override
			public String toString() {
				return "beta";
			}

			@Override
			public Scalar value(final Scalar input) {
				final double val = ((Complex) input).getReal().getDoubleValue();
				final Complex tmp = (Complex) FiniteDimensionalComplexFunctionSpaceTest.this.f
						.addition(FiniteDimensionalComplexFunctionSpaceTest.this.f.getOne(), new Complex(val, val));
				return (Scalar) FiniteDimensionalComplexFunctionSpaceTest.this.f.normalize(tmp);
			}
		};

		this.gamma = new GenericFunction() {
			private static final long serialVersionUID = -6598973940477311007L;

			@Override
			public Field getField() {
				return FiniteDimensionalComplexFunctionSpaceTest.this.f;
			}

			@Override
			public String toString() {
				return "gamma";
			}

			@Override
			public Scalar value(final Scalar input) {
				final double val = ((Complex) input).getDoubleValue();
				final Scalar factor = new Complex(Math.cos(val), Math.sin(val));
				final Complex tmp = (Complex) FiniteDimensionalComplexFunctionSpaceTest.this.f.product(factor, factor);
				return (Scalar) FiniteDimensionalComplexFunctionSpaceTest.this.f.normalize(tmp);
			}
		};

		this.base.add(this.alpha);
		this.base.add(this.beta);
	}

	// @Test
	public void test() {
		this.space = new FiniteDimensionalFunctionSpace(this.f, this.base, -Math.PI, Math.PI, true);
	}

	@Test
	public void test1() {
		this.alpha.plot(-Math.PI, Math.PI);
	}

	@Test
	public void test2() {
		this.beta.plot(-Math.PI, Math.PI);
	}

	@Test
	public void test3() {
		this.gamma.plot(-Math.PI, Math.PI);
	}

}
