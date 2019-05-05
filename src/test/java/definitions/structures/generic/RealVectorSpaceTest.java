package definitions.structures.generic;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.generic.finitedimensional.finitedimensionalspaces.FiniteVector;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Generator;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.finitedimensionalspaces.linearmappings.Isomorphism;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class RealVectorSpaceTest {

	static FiniteVector e1;
	static FiniteVector e2;
	static FiniteVector e3;
	
	static IFiniteDimensionalLinearMapping comp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		 e1 = new FiniteVector(new double[] { 1,0,0 });
		 e2 = new FiniteVector(new double[] { 0,1,0 });
		 e3 = new FiniteVector(new double[] { 0,0,1 });
		
		double[][] matrix=new double[][] {
			{1,0,1},{0,1,0},{-1,0,1}
		};
		
		IFiniteDimensionalLinearMapping map=Generator.getGenerator().getFiniteDimensionalLinearMapping(matrix);

		IFiniteDimensionalLinearMapping map2=((Isomorphism)map).getInverse();
		comp = Generator.getGenerator().getComposition(map,map2);
		
	}

	@Test
	public void first() throws Throwable {
		Assert.assertTrue(comp.get(e1).equals(e1));
	}

	@Test
	public void second() throws Throwable {
		Assert.assertTrue(comp.get(e2).equals(e2));
	}

	@Test
	public void third() throws Throwable {
		Assert.assertTrue(comp.get(e3).equals(e3));
	}

}
