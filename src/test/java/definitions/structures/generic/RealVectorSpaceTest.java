package definitions.structures.generic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.IVec;
import junit.framework.Assert;

public class RealVectorSpaceTest {

	static RealVec e1;
	static RealVec e2;
	static RealVec e3;
	
	static IFiniteDimensionalLinearMapping comp;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		Random r=new Random();

		 e1 = new RealVec(new double[] { 1,0,0 });
		 e2 = new RealVec(new double[] { 0,1,0 });
		 e3 = new RealVec(new double[] { 0,0,1 });
		
		double[][] matrix=new double[][] {
			{1,0,1},{0,1,0},{-1,0,1}
		};
		
		IFiniteDimensionalLinearMapping map=Generator.getGenerator().getFiniteDimensionalLinearMapping(matrix);

		IFiniteDimensionalLinearMapping map2=((Isomorphism)map).getInverse();
		comp = Generator.getGenerator().getComposition(map,map2);
		
	}

	@SuppressWarnings("deprecation")
	@Test
	public void first() throws Throwable {
		Assert.assertTrue(comp.get(e1).equals(e1));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void second() throws Throwable {
		Assert.assertTrue(comp.get(e2).equals(e2));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void third() throws Throwable {
		Assert.assertTrue(comp.get(e3).equals(e3));
	}

}
