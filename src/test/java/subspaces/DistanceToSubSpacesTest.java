package subspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.finitedimensional.Generator;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalEmbedding;
import definitions.structures.finitedimensional.subspaces.impl.FiniteDimensionalFunctionSubSpace;
import definitions.structures.finitedimensional.vectors.Function;
import definitions.structures.finitedimensional.vectors.functions.Constant;
import definitions.structures.finitedimensional.vectors.functions.ExponentialFunction;
import definitions.structures.finitedimensional.vectors.functions.LinearFunction;
import definitions.structures.finitedimensional.vectors.functions.Sine;
import definitions.structures.finitedimensional.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.vectorspaces.impl.SpaceGenerator;

public class DistanceToSubSpacesTest {

	static double answer;

	static Function sin;
	static Function cos;
	static Function constant;
	static Function identity;
	static Function exp;

	static EuclideanSpace genericSpace;

	static EuclideanFunctionSpace functionSpace;
	static EuclideanFunctionSpace functionSpace2;

	static FiniteDimensionalFunctionSubSpace functionSubSpace;

	@SuppressWarnings("unused")
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		genericSpace = Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(5);

		sin = new Sine(1, 0, 1);

		cos = new Sine(1, 0.5 * Math.PI, 1);

		constant = new Constant(1 / Math.sqrt(2 * Math.PI));

		identity = new LinearFunction(0, 1);

		exp = new ExponentialFunction(0, 1);

		final List<Vector> list = new ArrayList<>();

		functionSpace = SpaceGenerator.getInstance().getTrigonometricSpace(1);

		list.add(sin);
		list.add(cos);
		list.add(constant);
		list.add(identity);
		list.add(exp);

		functionSpace2 = new FiniteDimensionalFunctionSpace(list, -Math.PI, Math.PI);

		final Map<Vector, Map<Vector, Double>> coordinates2 = new HashMap<>();
		for (final Vector fun1 : functionSpace.genericBaseToList()) {
			final Map<Vector, Double> tmp = new HashMap<>();
			for (final Vector fun2 : functionSpace2.genericBaseToList()) {
				tmp.put(fun2, functionSpace2.product(fun1, fun2));
			}
			coordinates2.put(fun1, tmp);
		}

		final FiniteDimensionalEmbedding parametrization = (FiniteDimensionalEmbedding) Generator.getGenerator()
				.getMappinggenerator().getFiniteDimensionalLinearMapping(functionSpace, functionSpace2, coordinates2);

		functionSubSpace = new FiniteDimensionalFunctionSubSpace((FiniteDimensionalEmbedding) parametrization,
				functionSpace2);
		final Vector sum = functionSpace2.add(constant, identity);

//		final Vector id = functionSpace2.copyVector(identity);
		Vector projection = identity.getProjection(functionSubSpace);
		for (Vector baseVec:functionSubSpace.getGenericBase()) {
			((Function)baseVec).plot(-Math.PI,Math.PI);
		}
		answer = functionSubSpace.getDistance(identity, projection);

	}

	@Test
	public void first1() throws Throwable {
		Assert.assertTrue(Math.abs(answer - 2 * Math.sqrt(Math.PI)) < 1.e-5);
	}

}
