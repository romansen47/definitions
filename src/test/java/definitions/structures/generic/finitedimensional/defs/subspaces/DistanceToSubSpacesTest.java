package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSubSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.impl.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.Cosine;
import definitions.structures.generic.finitedimensional.defs.vectors.ExponentialFunction;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.Identity;
import definitions.structures.generic.finitedimensional.defs.vectors.Sine;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class DistanceToSubSpacesTest {

	static double answer;
	
//	static Function sin;
//	static Function cos;

	static Function sin2;
	static Function cos2;
	static Function constant2;
	static Function identity2;
	static Function exp;

	static EuclideanSpace genericSpace;
	static EuclideanSpace genericSpace2;

	static IFiniteDimensionalFunctionSpace functionSpace;
	static IFiniteDimensionalFunctionSpace functionSpace2;

	static FiniteDimensionalFunctionSubSpace functionSubSpace;
	
	@SuppressWarnings("unused")
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		//genericSpace = SpaceGenerator.getInstance().getTrigonometricSpace(3);
		genericSpace2 = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(5);

//		sin = new Sine(genericSpace.genericBaseToList().get(0).getGenericCoordinates());
//		cos = new Cosine(genericSpace.genericBaseToList().get(1).getGenericCoordinates());

		sin2 = new Sine(genericSpace2.genericBaseToList().get(0).getGenericCoordinates());
		cos2 = new Cosine(genericSpace2.genericBaseToList().get(1).getGenericCoordinates());
		constant2 = new Constant(genericSpace2.genericBaseToList().get(2).getGenericCoordinates(),1/Math.sqrt(2*Math.PI));
		identity2 = new FunctionTuple(genericSpace2.genericBaseToList().get(3).getGenericCoordinates()) {
			@Override
			public double value(double input) {
				return input;
			}
		};
		exp = new ExponentialFunction(genericSpace2.genericBaseToList().get(4).getGenericCoordinates());
		
		List<Vector> list = new ArrayList<>();
		List<Vector> list2 = new ArrayList<>();

//		list.add(sin);
//		list.add(cos);

		functionSpace = SpaceGenerator.getInstance().getTrigonometricSpace(1);

		list2.add(sin2);
		list2.add(cos2);
		list2.add(constant2);
		list2.add(identity2);
		list2.add(exp);

		functionSpace2 = new FiniteDimensionalFunctionSpace(list2, -Math.PI, Math.PI);

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
		
//		@SuppressWarnings("unused")
//		Vector max1 = functionSubSpace.getNearestVector(constant2);
//		Vector max2 = functionSubSpace.getCoordinates(identity2);
//		
//		Vector sum = functionSubSpace.add(constant2, identity2);
		
		@SuppressWarnings("unused")
//		Vector max2 = functionSpace2.getCoordinates(identity2);
//		
		Vector sum = functionSpace2.add(constant2, identity2);
		
		Vector max = functionSpace.getCoordinates(identity2);
		
		answer = functionSpace.norm(max);

	}

	@Test
	public void first1() throws Throwable {
		Assert.assertTrue(Math.abs(answer-2*Math.sqrt(Math.PI))<1.e-5);
	}

}
