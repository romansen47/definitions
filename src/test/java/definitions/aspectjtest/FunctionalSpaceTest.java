/**
 * 
 */
package definitions.aspectjtest;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.mappings.Functional;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class FunctionalSpaceTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	final EuclideanSpace space = ((SpringConfiguration) SpringConfiguration.getSpringConfiguration()).getApplicationContext()
			.getBean(SpaceGenerator.class).getFiniteDimensionalVectorSpace(3);
	final EuclideanSpace dualSpace = this.space.getDualSpace();

	final EuclideanSpace dualDualSpace = this.dualSpace.getDualSpace();
	final EuclideanSpace functionSpace = SpaceGenerator.getInstance().getTrigonometricSpace(RealLine.getInstance(), 1);
	final EuclideanSpace dualFunctionSpace = this.functionSpace.getDualSpace();

	final EuclideanSpace dualDualFunctionSpace = this.dualFunctionSpace.getDualSpace();
	final EuclideanSpace sobolevSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace(RealLine.getInstance(), 10, 10);
	final EuclideanSpace dualSobolevSpace = this.sobolevSpace.getDualSpace();

	final EuclideanSpace dualDualSobolevSpace = this.dualSobolevSpace.getDualSpace();

	@Test
	public void functionSpaceTest() {
		final Vector a = this.functionSpace.genericBaseToList().get(1);
		final Vector b = this.dualFunctionSpace.genericBaseToList().get(1);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		System.out.println(c.toString());
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
	}

	@Test
	public void sobolevSpaceTest() {
		final Vector a = this.sobolevSpace.genericBaseToList().get(1);
		final Vector b = this.dualSobolevSpace.genericBaseToList().get(1);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		System.out.println(c.toString());
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
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

}
