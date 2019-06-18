package subspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

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

		genericSpace = Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(5);

		sin = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.sin(input);
			}
		};
		
		cos = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.cos(input);
			}
		};
		
		constant = new GenericFunction() {
			@Override
			public double value(double input) {
				return 1 / Math.sqrt(2 * Math.PI);
			}
		};

		identity = new GenericFunction() {
			@Override
			public double value(double input) {
				return input;
			}
		};
		
		exp = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.exp(input);
			}
		};

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
				if (((Function) fun1).equals((Function) fun2, functionSpace)) {
					tmp.put(fun2, 1.);
				} else {
					tmp.put(fun2, 0.);
				}
			}
			coordinates2.put(fun1, tmp);
		}

		final Homomorphism parametrization = Generator.getGenerator().getMappinggenerator()
				.getFiniteDimensionalLinearMapping(functionSpace, functionSpace2, coordinates2);

		functionSubSpace = new FiniteDimensionalFunctionSubSpace((IFiniteDimensionalLinearMapping) parametrization,
				functionSpace);
		final Vector sum = functionSpace2.add(constant, identity);
		final Vector max = functionSpace.getCoordinates(identity);
		answer = functionSpace.norm(max);

	}

	@Test
	public void first1() throws Throwable {
		Assert.assertTrue(Math.abs(answer - 2 * Math.sqrt(Math.PI)) < 1.e-5);
	}

}
