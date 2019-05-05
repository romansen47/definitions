package definitions.structures.generic;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.IFiniteVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Generator;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Isomorphism;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class RealVectorSpaceTest {

	static FiniteVector e1;
	static FiniteVector e2;
	static FiniteVector e3;
	
	static IFiniteDimensionalLinearMapping comp1;
	static IFiniteDimensionalLinearMapping comp2;
	static IFiniteDimensionalLinearMapping comp3;
	
	static double[][] matrix=new double[][] {
		{1,0,1},{0,1,0},{-1,0,1}
	};
	
	static double[][] matrix2=new double[][] {
		{0,1,2},{0,0,3},{1,0,0}
	};
	
	static double[][] matrix3=new double[][] {{1.e5}};
	
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		e1 = new FiniteVector(new double[] { 1,0,0 });
		e2 = new FiniteVector(new double[] { 0,1,0 });
		e3 = new FiniteVector(new double[] { 0,0,1 });
		
		IFiniteDimensionalLinearMapping map;
		IFiniteDimensionalLinearMapping map2;
		
		map=Generator.getGenerator().getFiniteDimensionalLinearMapping(matrix);
		map2=((Isomorphism)map).getInverse();
		comp1 = Generator.getGenerator().getComposition(map,map2);
		
		map=Generator.getGenerator().getFiniteDimensionalLinearMapping(matrix2);
		map2=((Isomorphism)map).getInverse();
		comp2 = Generator.getGenerator().getComposition(map,map2);
		
		IFiniteDimensionalLinearMapping test1=Generator.getGenerator().getFiniteDimensionalLinearMapping(matrix3);
		IFiniteDimensionalLinearMapping test2=((Isomorphism)test1).getInverse();
		comp3 = Generator.getGenerator().getComposition(test1,test2);
		
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
		double[] x=new double[1];
		x[0]=3.;
		IFiniteVector y = new FiniteVector(x);
		Assert.assertTrue(comp3.get(y).equals(y));
	}
	
}
