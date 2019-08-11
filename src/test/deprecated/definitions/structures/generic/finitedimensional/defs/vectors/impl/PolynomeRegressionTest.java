package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.impl.RealLine;
import definitions.structures.field.scalar.impl.Real;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.vectors.impl.Monome;

public class PolynomeRegressionTest {

	final static double left = -1;
	final static double right = 1;

	final static List<Vector> base = new ArrayList<>();

	static Function sin = null;
	static Function exp = null;

	static EuclideanFunctionSpace space = null;
	static EuclideanFunctionSpace newSpace = null;

	private static int maxDegree = 3;

	// @BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		sin = new GenericFunction() {
			// @Override
			@Override
			public Scalar value(Scalar input) {
				return new Real(Math.sin(input.getValue() * Math.PI));
			}
		};

		exp = new GenericFunction() {
			// @Override
			@Override
			public Scalar value(Scalar input) {
				return new Real(Math.exp(input.getValue() * Math.PI));
			}
		};

		for (int i = 0; i < (maxDegree + 1); i++) {
			base.add(new Monome(i));
		}

		space = (EuclideanFunctionSpace) Generator.getGenerator()
				.getFiniteDimensionalFunctionSpace(RealLine.getInstance(), base, left, right);

//		newSpace=(IFiniteDimensionalFunctionSpace) Generator.getGenerator().getFiniteDimensionalSobolevSpace((IFiniteDimensionalFunctionSpace) space);

	}

	// @Test
	public void sinTest1() throws Throwable {
		final Function ans = sin.getProjection(space);
		ans.plotCompare(left, right, sin);
	}

	// @Test
	public void expTest1() throws Throwable {
		final Function ans = exp.getProjection(space);
		ans.plotCompare(left, right, exp);
	}

//	//@Test
	public void sinTest2() throws Throwable {
		final Function ans = sin.getProjection(newSpace);
		ans.plotCompare(left, right, sin);
	}

//	//@Test
	public void expTest2() throws Throwable {
		final Function ans = exp.getProjection(newSpace);
		ans.plotCompare(left, right, exp);
	}

}
