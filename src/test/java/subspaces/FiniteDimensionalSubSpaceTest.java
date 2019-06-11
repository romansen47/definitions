package subspaces;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.BeforeClass;
import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.ParameterizedSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.impl.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.impl.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Constant;
import definitions.structures.generic.finitedimensional.defs.vectors.Cosine;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.Identity;
import definitions.structures.generic.finitedimensional.defs.vectors.Sine;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class FiniteDimensionalSubSpaceTest {

	static double[][] matrix;
	static double[][] matrix2;
	static List<Vector> list;

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

	static Vector alpha;
	static Vector beta;
	static int m = (int) 1.e5;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		matrix = new double[][] { { 1, 3 }, { 3, 2 }, { 1, 0 } };
		matrix2 = new double[][] { { 1, 0, 0 }, { 0, 1, 0 }, { 0, 0, 1 }, { 0, 0, 0 } };
		list = new ArrayList<>();

		final IFiniteDimensionalLinearMapping map = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(matrix);
		final ParameterizedSpace subSpace = new FiniteDimensionalSubSpace(map);

		final List<Vector> base = subSpace.genericBaseToList();
		final Map<Vector, Map<Vector, Double>> coordinates = new ConcurrentHashMap<>();

		final Map<Vector, Double> key1 = new ConcurrentHashMap<>();

		alpha = base.get(0);
		beta = base.get(1);

		key1.put(alpha, 0.0);
		key1.put(beta, 1.0);

		final Map<Vector, Double> key2 = new ConcurrentHashMap<>();
		key2.put(alpha, -1.0);
		key2.put(beta, 0.0);

		coordinates.put(alpha, key1);
		coordinates.put(beta, key2);

		System.out.println("Test");
		final IFiniteDimensionalLinearMapping testMap = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(subSpace, subSpace, coordinates);

		list.add(base.get(0));
		for (int i = 0; i < m + 4; i++) {
			list.add(testMap.get(list.get(list.size() - 1)));
		}

		genericSpace = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(3);
		genericSpace2 = SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(4);

		sin = new Sine(genericSpace.genericBaseToList().get(0).getGenericCoordinates());
		cos = new Cosine(genericSpace.genericBaseToList().get(1).getGenericCoordinates());
		identity = new Identity(genericSpace.genericBaseToList().get(2).getGenericCoordinates());

		sin2 = new Sine(genericSpace2.genericBaseToList().get(0).getGenericCoordinates());
		cos2 = new Cosine(genericSpace2.genericBaseToList().get(1).getGenericCoordinates());
		identity2 = new Identity(genericSpace2.genericBaseToList().get(2).getGenericCoordinates());
		one2 = new Constant(genericSpace2.genericBaseToList().get(3).getGenericCoordinates(), 1);

		final List<Vector> newlist = new ArrayList<>();
		final List<Vector> newlist2 = new ArrayList<>();
		newlist.add(sin);
		newlist.add(cos);
		newlist.add(identity);

		functionSpace = new FiniteDimensionalFunctionSpace(newlist, -Math.PI, Math.PI);

		newlist2.add(sin2);
		newlist2.add(cos2);
		newlist2.add(identity2);
		newlist2.add(one2);

		functionSpace2 = new FiniteDimensionalFunctionSpace(newlist2, -Math.PI, Math.PI);

		final Map<Vector, Map<Vector, Double>> coordinates2 = new ConcurrentHashMap<>();
		for (final Vector fun1 : functionSpace.genericBaseToList()) {
			final Map<Vector, Double> tmp = new ConcurrentHashMap<>();
			for (final Vector fun2 : functionSpace2.genericBaseToList()) {
				if (((Function) fun1).equals((Function) fun2, functionSpace)) {
					tmp.put(fun2, 1.);
				} else {
					tmp.put(fun2, 0.);
				}
			}
			coordinates2.put(fun1, tmp);
		}

		final IFiniteDimensionalLinearMapping parametrization = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(functionSpace, functionSpace2, coordinates2);

		functionSubSpace = new FiniteDimensionalSubSpace(parametrization);
		final Vector sum = functionSpace2.add(one2, identity2);
		final Vector max = functionSubSpace.getNearestVector(sum);

	}

	//@Test
	public void first1() throws Throwable {
		final boolean ans = list.get(0).equals(list.get(m + 4));
//		System.out.println(list.get(0).toString());
//		System.out.println(list.get(1).toString());
//		System.out.println(list.get(2).toString());
//		System.out.println(list.get(3).toString());
		Assert.assertTrue(ans);
	}

}
