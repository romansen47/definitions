/**
 * 
 */
package definitions.structures.finitedimensional.vectorspaces.impl;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.mappings.Functional;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.FunctionalSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class FunctionalSpaceTest {

	EuclideanSpace space = (EuclideanSpace) SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(3);
	EuclideanSpace dualSpace = new FunctionalSpace(this.space);

	EuclideanSpace functionSpace = SpaceGenerator.getInstance().getTrigonometricSpace(RealLine.getInstance(), 1);
	EuclideanSpace dualFunctionSpace = new FunctionalSpace(this.functionSpace);

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Test
	public void spaceTest() {
		final Vector a = this.space.genericBaseToList().get(0);
		final Vector b = this.dualSpace.genericBaseToList().get(0);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
		System.out.println(c.toString());
	}

	@Test
	public void functionSpaceTest() {
		final Vector a = this.functionSpace.genericBaseToList().get(1);
		final Vector b = this.dualFunctionSpace.genericBaseToList().get(1);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
		System.out.println(c.toString());
	}

	@Test
	public void sobolevSpaceTest() {
		final EuclideanSpace sobolevSpace = SpaceGenerator.getInstance()
				.getTrigonometricSobolevSpace(RealLine.getInstance(), 12, 1);
		final EuclideanSpace dualSobolevSpace = new FunctionalSpace(sobolevSpace);
		final Vector a = sobolevSpace.genericBaseToList().get(1);
		final Vector b = dualSobolevSpace.genericBaseToList().get(1);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
		System.out.println(c.toString());
	}

}
