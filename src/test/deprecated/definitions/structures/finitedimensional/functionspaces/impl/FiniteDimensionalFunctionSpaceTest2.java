package definitions.structures.finitedimensional.functionspaces.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Constant;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.LinearFunction;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

public class FiniteDimensionalFunctionSpaceTest2 {

	final static List<Vector> list = new ArrayList<>();
	static EuclideanFunctionSpace space;
	static Function sine = new Sine(1, 0, Math.PI);
	static Function cosine = new Sine(1, 0.5 * Math.PI, Math.PI);
	static Function id = new LinearFunction(0, 1);
	static Function abs = new GenericFunction() {
		@Override
		public double value(double input) {
			return Math.abs(input);
		}
	};
	static Function projection;
	static int max = 5;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final Function fun = new Constant(1);
		list.add(fun);

		for (int i = 1; i < max; i++) {
			list.add(new ExponentialFunction(0, i));
			list.add(new ExponentialFunction(0, -i));
		}

		space = SpaceGenerator.getInstance().getFiniteDimensionalSobolevSpace(list, -1, 1, 2);
//		space=SpaceGenerator.getInstance().getFiniteDimensionalFunctionSpace(list, -1, 1);
	}

	@Test
	public final void testSine() {
		projection = sine.getProjection(space);
		sine.plotCompare(-1, 1, projection);
		final double ans = space.getDistance(sine, projection);
		Assert.assertTrue(Math.abs(ans) < 1.e-2);
	}

	@Test
	public final void testCosine() {
		projection = cosine.getProjection(space);
		cosine.plotCompare(-1, 1, projection);
		final double ans = space.getDistance(cosine, projection);
		Assert.assertTrue(Math.abs(ans) < 1.e-2);
	}

	@Test
	public final void testId() {
		projection = id.getProjection(space);
		id.plotCompare(-1, 1, projection);
		final double ans = space.getDistance(id, projection);
		Assert.assertTrue(Math.abs(ans) < 1.e-3);
	}

	@Test
	public final void testabs() {
		projection = abs.getProjection(space);
		abs.plotCompare(-1, 1, projection);
		final double ans = space.getDistance(abs, projection);
		Assert.assertTrue(Math.abs(ans) < 1.e-1);
	}

}
