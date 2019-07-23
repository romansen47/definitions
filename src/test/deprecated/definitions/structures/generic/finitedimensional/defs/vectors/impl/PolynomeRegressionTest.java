package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.impl.Monome;

public class PolynomeRegressionTest {

	final static double left = -1;
	final static double right = 1;

	final static List<Vector> base = new ArrayList<>();

	static Function sin = null;
	static Function exp = null;

	static EuclideanFunctionSpace space = null;
	static EuclideanFunctionSpace newSpace = null;

	private static int maxDegree = 10;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		sin = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.sin(input * Math.PI);
			}
		};

		exp = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.exp(input * Math.PI);
			}
		};

		for (int i = 0; i < (maxDegree + 1); i++) {
			base.add(new Monome(i));
		}

		space = (EuclideanFunctionSpace) Generator.getGenerator().getFiniteDimensionalFunctionSpace(base, left, right);

//		newSpace=(IFiniteDimensionalFunctionSpace) Generator.getGenerator().getFiniteDimensionalSobolevSpace((IFiniteDimensionalFunctionSpace) space);

	}

	@Test
	public void sinTest1() throws Throwable {
		final Function ans = new FunctionTuple(sin.getCoordinates(space));
		ans.plotCompare(left, right, sin);
	}

	@Test
	public void expTest1() throws Throwable {
		final Function ans = new FunctionTuple(exp.getCoordinates(space));
		ans.plotCompare(left, right, exp);
	}

//	@Test
	public void sinTest2() throws Throwable {
		final Function ans = new FunctionTuple(sin.getCoordinates(newSpace));
		ans.plotCompare(left, right, sin);
	}

//	@Test
	public void expTest2() throws Throwable {
		final Function ans = new FunctionTuple(exp.getCoordinates(newSpace));
		ans.plotCompare(left, right, exp);
	}

}
