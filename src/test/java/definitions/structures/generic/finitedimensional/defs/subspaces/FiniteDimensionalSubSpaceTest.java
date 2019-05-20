package definitions.structures.generic.finitedimensional.defs.subspaces;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.defs.mappings.FiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalInjectiveLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFunction;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Constant;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Cosine;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Identity;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.functions.Sine;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FiniteDimensionalSubSpaceTest {

	static IFiniteVector e1;
	static IFiniteVector e2;
	static IFiniteVector e3;

	static double[][] matrix = new double[][] { { 1, 3 }, { 3, 2 }, { 1, 0 } };
	static double[][] matrix2 = new double[][] { { 1, 0 ,0}, {0, 1, 0 }, { 0, 0 ,1 }, { 0,0,0 } };
	static List<IFiniteVector> list = new ArrayList<>();

	static IFunction sin;
	static IFunction cos;
	static IFunction identity;
	static IFunction sin2;
	static IFunction cos2;
	static IFunction identity2;
	static IFunction one2;

	static IFiniteDimensionalVectorSpace genericSpace;
	static IFiniteDimensionalVectorSpace genericSpace2;
	
	static IFiniteDimensionalFunctionSpace functionSpace;
	static IFiniteDimensionalFunctionSpace functionSpace2;

	static IFiniteDimensionalSubSpace functionSubSpace;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new FiniteVector(new double[] { 1, 0, 0 });
		e2 = new FiniteVector(new double[] { 0, 1, 0 });
		e3 = new FiniteVector(new double[] { 0, 0, 1 });

		IFiniteDimensionalLinearMapping map = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(matrix);
		IFiniteDimensionalSubSpace subSpace = new FiniteDimensionalSubSpace(map);

		@SuppressWarnings("unused")
		Set<IFiniteVector> test = subSpace.getGenericBase();

		List<IFiniteVector> base = subSpace.genericBaseToList();
		Map<IFiniteVector, Map<IFiniteVector, Double>> coordinates = new HashMap<>();

		Map<IFiniteVector, Double> key1 = new HashMap<>();
		key1.put(base.get(0), 0.0);
		key1.put(base.get(1), 2.);

		Map<IFiniteVector, Double> key2 = new HashMap<>();
		key2.put(base.get(0), -0.5);
		key2.put(base.get(1), 0.0);

		coordinates.put(base.get(0), key1);
		coordinates.put(base.get(1), key2);

		IFiniteDimensionalLinearMapping testMap = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(subSpace, subSpace, coordinates);

		list.add(base.get(0));
		for (int i = 0; i < 4; i++) {
			list.add(testMap.get(list.get(list.size() - 1)));
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

		List<IFiniteVector> list = new ArrayList<>();
		List<IFiniteVector> list2 = new ArrayList<>();
		list.add(sin);
		x=sin.getDim();
		list.add(cos);
		list.add(identity);

		functionSpace = new FiniteDimensionalFunctionSpace(list, -Math.PI, Math.PI);
		
		list2.add(sin2);
		list2.add(cos2);
		list2.add(identity2);
		list2.add(one2);
		
		functionSpace2 = new FiniteDimensionalFunctionSpace(list2, -Math.PI, Math.PI);
		
		Map<IFiniteVector,Map<IFiniteVector,Double>> coordinates2=new HashMap<>();
		for (IFiniteVector fun1:functionSpace.genericBaseToList()) {
			Map<IFiniteVector,Double> tmp = new HashMap<>();;
			for (IFiniteVector fun2:functionSpace2.genericBaseToList()) {
				if (((IFunction)fun1).equals((IFunction)fun2,functionSpace)){
					tmp.put((IFunction)fun2, 1.);
				}
				else {
					tmp.put((IFunction)fun2, 0.);
				}
			}
			coordinates2.put((IFunction)fun1,tmp);
		}
		
		IFiniteDimensionalInjectiveLinearMapping parametrization = (IFiniteDimensionalInjectiveLinearMapping) MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(functionSpace,functionSpace2,coordinates2);
		
		functionSubSpace = new FiniteDimensionalSubSpace(parametrization);
		IFunction sum=functionSpace.add(one2,identity2);
		IFiniteVector max=functionSubSpace.getNearestVector(sum);
		
	}

	@Test
	public void first1() throws Throwable {
		boolean ans = list.get(0).equals(list.get(4));
	}

}
