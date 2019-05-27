package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.Cosine;
import definitions.structures.generic.finitedimensional.defs.vectors.ExponentialFunction;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.Sine;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;

public class DistanceToSubSpacesTest {

	static double answer;

	static Function sin;
	static Function cos;
	static Function constant;
	static Function identity;
	static Function exp;

	static EuclideanSpace genericSpace;

	static IFiniteDimensionalFunctionSpace functionSpace;
	static IFiniteDimensionalFunctionSpace functionSpace2;

	static FiniteDimensionalFunctionSubSpace functionSubSpace;

	@SuppressWarnings("unused")
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		genericSpace = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(5);

		sin = new Sine(genericSpace.genericBaseToList().get(0).getGenericCoordinates());
		cos = new Cosine(genericSpace.genericBaseToList().get(1).getGenericCoordinates());
		constant = new Constant(genericSpace.genericBaseToList().get(2).getGenericCoordinates(),
				1 / Math.sqrt(2 * Math.PI));
		identity = new FunctionTuple(genericSpace.genericBaseToList().get(3).getGenericCoordinates()) {
			@Override
			public double value(double input) {
				return input;
			}
		};
		exp = new ExponentialFunction(genericSpace.genericBaseToList().get(4).getGenericCoordinates());

		List<Vector> list = new ArrayList<>();

		functionSpace = SpaceGenerator.getInstance().getTrigonometricSpace(1);

		list.add(sin);
		list.add(cos);
		list.add(constant);
		list.add(identity);
		list.add(exp);

		functionSpace2 = new FiniteDimensionalFunctionSpace(list, -Math.PI, Math.PI);

		Map<Vector, Map<Vector, Double>> coordinates2 = new HashMap<>();
		for (Vector fun1 : functionSpace.genericBaseToList()) {
			Map<Vector, Double> tmp = new HashMap<>();
			for (Vector fun2 : functionSpace2.genericBaseToList()) {
				if (((Function) fun1).equals((Function) fun2, functionSpace)) {
					tmp.put(fun2, 1.);
				} else {
					tmp.put(fun2, 0.);
				}
			}
			coordinates2.put(fun1, tmp);
		}

		IFiniteDimensionalLinearMapping parametrization = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(functionSpace, functionSpace2, coordinates2);

		functionSubSpace = new FiniteDimensionalFunctionSubSpace(parametrization, functionSpace);
		Vector sum = functionSpace2.add(constant, identity);
		Vector max = functionSpace.getCoordinates(identity);
		answer = functionSpace.norm(max);

	}

	@Test
	public void first1() throws Throwable {
		Assert.assertTrue(Math.abs(answer - 2 * Math.sqrt(Math.PI)) < 1.e-5);
	}

}
