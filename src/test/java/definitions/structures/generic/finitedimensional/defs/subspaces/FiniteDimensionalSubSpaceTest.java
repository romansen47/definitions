package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.impl.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.Cosine;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.Identity;
import definitions.structures.generic.finitedimensional.defs.vectors.Sine;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.Tuple;

public class FiniteDimensionalSubSpaceTest {

	static FiniteVector e1;
	static FiniteVector e2;
	static FiniteVector e3;

	static double[][] matrix = new double[][] { { 1, 3 }, { 3, 2 }, { 1, 0 } };
	static double[][] matrix2 = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 }, { 0, 0, 0 } };
	static List<Vector> list = new ArrayList<>();

	static Function sin;
	static Function cos;
	static Function identity;
	static Function sin2;
	static Function cos2;
	static Function identity2;
	static Function one2;

	static EuclideanSpace genericSpace;
	static EuclideanSpace genericSpace2;

	static IFiniteDimensionalFunctionSpace functionSpace;
	static IFiniteDimensionalFunctionSpace functionSpace2;

	static ParameterizedSpace functionSubSpace;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new Tuple(new double[] { 1, 0, 0 });
		e2 = new Tuple(new double[] { 0, 1, 0 });
		e3 = new Tuple(new double[] { 0, 0, 1 });

		IFiniteDimensionalLinearMapping map = 
				MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(matrix);
		ParameterizedSpace subSpace = new FiniteDimensionalSubSpace(map);

		@SuppressWarnings("unused")
		Set<Vector> test = subSpace.getGenericBase();

		List<Vector> base = subSpace.genericBaseToList();
		Map<Vector, Map<Vector, Double>> coordinates = new HashMap<>();

		Map<Vector, Double> key1 = new HashMap<>();
		key1.put(base.get(0), 0.0);
		key1.put(base.get(1), 2.);

		Map<Vector, Double> key2 = new HashMap<>();
		key2.put(base.get(0), -0.5);
		key2.put(base.get(1), 0.0);

		coordinates.put(base.get(0), key1);
		coordinates.put(base.get(1), key2);

		IFiniteDimensionalLinearMapping testMap = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(subSpace, subSpace, coordinates);

		list.add(base.get(0));
		for (int i = 0; i < 4; i++) {
			list.add((FiniteVector) testMap.get(list.get(list.size() - 1)));
		}

		double x = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(3).getDistance(e1, e2);

		genericSpace = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(3);
		genericSpace2 = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(4);

		sin = new Sine(genericSpace.genericBaseToList().get(0).getGenericCoordinates());
		cos = new Cosine(genericSpace.genericBaseToList().get(1).getGenericCoordinates());
		identity = new Identity(genericSpace.genericBaseToList().get(2).getGenericCoordinates());

		sin2 = new Sine(genericSpace2.genericBaseToList().get(0).getGenericCoordinates());
		cos2 = new Cosine(genericSpace2.genericBaseToList().get(1).getGenericCoordinates());
		identity2 = new Identity(genericSpace2.genericBaseToList().get(2).getGenericCoordinates());
		one2 = new Constant(genericSpace2.genericBaseToList().get(3).getGenericCoordinates(), 1);

		List<Vector> list = new ArrayList<>();
		List<Vector> list2 = new ArrayList<>();
		list.add(sin);
		x = sin.getDim();
		list.add(cos);
		list.add(identity);

		functionSpace = new FiniteDimensionalFunctionSpace(list, -Math.PI, Math.PI);

		list2.add(sin2);
		list2.add(cos2);
		list2.add(identity2);
		list2.add(one2);

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

		functionSubSpace = new FiniteDimensionalSubSpace(parametrization);
		Vector sum = functionSpace2.add(one2, identity2);
		Vector max = functionSubSpace.getNearestVector(sum);

	}

	@Test
	public void first1() throws Throwable {
		boolean ans = list.get(0).equals(list.get(4));
	}

}
