/**
 * 
 */
package definitions.structures.euclidean.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.ComplexPlane;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Complex;
import definitions.structures.abstr.vectorspaces.FunctionSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * @author BAU12350
 *
 */
public class FiniteDimensionalComplexFunctionSpaceTest {// extends FiniteDimensionalFunctionSpaceTest {

	final public Field f = (Field) ComplexPlane.getInstance();
	List<Vector> base = new ArrayList<>();
	FunctionSpace space;

	@Test
	public void test() {

		final Function alpha = new GenericFunction() {
			private static final long serialVersionUID = 6698998256903151087L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Complex) input).getReal().getValue();
				final Complex tmp = (Complex) FiniteDimensionalComplexFunctionSpaceTest.this.f
						.add(FiniteDimensionalComplexFunctionSpaceTest.this.f.getOne(), new Complex(val, -val));
				return (Scalar) FiniteDimensionalComplexFunctionSpaceTest.this.f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return FiniteDimensionalComplexFunctionSpaceTest.this.f;
			}

			@Override
			public String toString() {
				return "alpha";
			}
		};

		final Function beta = new GenericFunction() {
			private static final long serialVersionUID = -2624612868740391242L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Complex) input).getReal().getValue();
				final Complex tmp = (Complex) FiniteDimensionalComplexFunctionSpaceTest.this.f
						.add(FiniteDimensionalComplexFunctionSpaceTest.this.f.getOne(), new Complex(val, val));
				return (Scalar) FiniteDimensionalComplexFunctionSpaceTest.this.f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return FiniteDimensionalComplexFunctionSpaceTest.this.f;
			}

			@Override
			public String toString() {
				return "beta";
			}
		};

		final Function gamma = new GenericFunction() {
			private static final long serialVersionUID = -6598973940477311007L;

			@Override
			public Scalar value(Scalar input) {
				final double val = ((Complex) input).getValue();
				final Scalar factor = new Complex(Math.cos(val), Math.sin(val));
				final Complex tmp = (Complex) FiniteDimensionalComplexFunctionSpaceTest.this.f.product(factor, factor);
				return (Scalar) FiniteDimensionalComplexFunctionSpaceTest.this.f.normalize(tmp);
			}

			@Override
			public Field getField() {
				return FiniteDimensionalComplexFunctionSpaceTest.this.f;
			}

			@Override
			public String toString() {
				return "gamma";
			}
		};

		this.base.add(alpha);
		this.base.add(beta);

		alpha.plot(-Math.PI, Math.PI);
		beta.plot(-Math.PI, Math.PI);
		gamma.plot(-Math.PI, Math.PI);

		this.space = new FiniteDimensionalFunctionSpace(this.f, this.base, -Math.PI, Math.PI, true);

	}

}
