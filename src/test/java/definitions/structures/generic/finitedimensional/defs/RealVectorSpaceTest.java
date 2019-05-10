package definitions.structures.generic.finitedimensional.defs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.defs.mappings.FiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.InvertibleFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.Isomorphism;
import definitions.structures.generic.finitedimensional.defs.mappings.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.FiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.IFiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.FiniteVector;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class RealVectorSpaceTest {

	static IFiniteVector e1;
	static IFiniteVector e2;
	static IFiniteVector e3;

	static IFiniteDimensionalLinearMapping comp1;
	static IFiniteDimensionalLinearMapping comp2;
	static IFiniteDimensionalLinearMapping comp3;

	static double[][] matrix = new double[][] { { 1, 0, 1 }, { 0, 1., 0 }, { -1, 0, 1 } };

	static double[][] matrix2 = new double[][] { { 0, 1, 2 }, { 0, 0, 3 }, { 1, 0, 0 } };

	static double[][] matrix3 = new double[][] { { 1.e5 } };

	static double[][] matrix4 = new double[][] { { 1, 3 }, { 3, 2 }, { 1, 0 } };

	static List<IFiniteDimensionalLinearMapping> maps = new ArrayList<>();

	static IFiniteDimensionalLinearMapping product;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new FiniteVector(new double[] { 1, 0, 0 });
		e2 = new FiniteVector(new double[] { 0, 1, 0 });
		e3 = new FiniteVector(new double[] { 0, 0, 1 });

		IFiniteDimensionalLinearMapping map = MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(matrix);
		IFiniteDimensionalLinearMapping inv = ((Isomorphism) map).getInverse();
		comp1 = MappingGenerator.getInstance().getComposition(map, inv);

		IFiniteDimensionalLinearMapping map2 = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(matrix2);
		IFiniteDimensionalLinearMapping inv2 = ((Isomorphism) map2).getInverse();
		comp2 = MappingGenerator.getInstance().getComposition(map2, inv2);

		IFiniteDimensionalLinearMapping map3 = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(matrix3);
		IFiniteDimensionalLinearMapping inv3 = ((Isomorphism) map3).getInverse();
		comp3 = MappingGenerator.getInstance().getComposition(map3, inv3);

		IFiniteDimensionalLinearMapping map4 = MappingGenerator.getInstance()
				.getFiniteDimensionalLinearMapping(matrix4);
		IFiniteDimensionalSubSpace subSpace = new FiniteDimensionalSubSpace(map4);

		@SuppressWarnings("unused")
		Set<IFiniteVector> test = subSpace.getGenericBase();

//		for (int j = 0; j < 10; j++) {
//			int i = 0;
//			maps.add(MappingGenerator.getInstance().getFiniteDimensionalLinearMapping(new double[][] {
//					e1.getGenericCoordinates(), e2.getGenericCoordinates(), e3.getGenericCoordinates() }));
//			while (i++ < 1.e5) {
//				maps.add(MappingGenerator.getInstance().getComposition(maps.get(maps.size() - 1), map));
//			}
//			product = maps.get(maps.size() - 1);
//			System.out.println(product);
//		}
		
		List<IFiniteVector> base=subSpace.genericBaseToList();
		Map<IFiniteVector,Map<IFiniteVector,Double>> coordinates=new HashMap<>();

		Map<IFiniteVector,Double> key1=new HashMap<>();
		key1.put(base.get(0),0.0);
		key1.put(base.get(1),5.);
		
		Map<IFiniteVector,Double> key2=new HashMap<>();
		key2.put(base.get(0),-5.0);
		key2.put(base.get(1),0.0);

		coordinates.put(base.get(0),key1);
		coordinates.put(base.get(1),key2);
		
		IFiniteDimensionalLinearMapping testMap = 
				MappingGenerator.getInstance().
				getFiniteDimensionalLinearMapping(subSpace,subSpace,coordinates);
		IFiniteVector x=testMap.get(base.get(0));
		System.out.println(testMap.toString());
	}

	@Test
	public void first1() throws Throwable {
		Assert.assertTrue(comp1.get(e1).equals(e1));
	}

	@Test
	public void second1() throws Throwable {
		Assert.assertTrue(comp1.get(e2).equals(e2));
	}

	@Test
	public void third1() throws Throwable {
		Assert.assertTrue(comp1.get(e3).equals(e3));
	}

	@Test
	public void first2() throws Throwable {
		Assert.assertTrue(comp2.get(e1).equals(e1));
	}

	@Test
	public void second2() throws Throwable {
		Assert.assertTrue(comp2.get(e2).equals(e2));
	}

	@Test
	public void third2() throws Throwable {
		Assert.assertTrue(comp2.get(e3).equals(e3));
	}

	@Test
	public void first3() throws Throwable {
		double[] x = new double[1];
		x[0] = 3.;
		IFiniteVector y = new FiniteVector(x);
		Assert.assertTrue(comp3.get(y).equals(y));
	}

	@Test
	public void productTest() {
		Assert.assertTrue(product instanceof InvertibleFiniteDimensionalLinearMapping);
	}

}
