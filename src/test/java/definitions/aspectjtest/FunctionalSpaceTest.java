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

	final EuclideanSpace space = ((SpringConfiguration) SpringConfiguration.getSpringConfiguration())
			.getApplicationContext().getBean(SpaceGenerator.class).getFiniteDimensionalVectorSpace(3);
	final EuclideanSpace dualSpace = space.getDualSpace();

	final EuclideanSpace dualDualSpace = dualSpace.getDualSpace();
	final EuclideanSpace functionSpace = SpaceGenerator.getInstance().getNormedTrigonometricSpace(RealLine.getInstance(), 1);
	final EuclideanSpace dualFunctionSpace = functionSpace.getDualSpace();

	final EuclideanSpace dualDualFunctionSpace = dualFunctionSpace.getDualSpace();
	final EuclideanSpace sobolevSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace(RealLine.getInstance(), 10, 10);
	final EuclideanSpace dualSobolevSpace = sobolevSpace.getDualSpace();

	final EuclideanSpace dualDualSobolevSpace = dualSobolevSpace.getDualSpace();

	@Test
	public void functionSpaceTest() {
		final Vector a = functionSpace.genericBaseToList().get(1);
		final Vector b = dualFunctionSpace.genericBaseToList().get(1);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		System.out.println(c.toString());
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
	}

	@Test
	public void sobolevSpaceTest() {
		final Vector a = sobolevSpace.genericBaseToList().get(1);
		final Vector b = dualSobolevSpace.genericBaseToList().get(1);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		System.out.println(c.toString());
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
	}

	@Test
	public void spaceTest() {
		final Vector a = space.genericBaseToList().get(0);
		final Vector b = dualSpace.genericBaseToList().get(0);
		final Functional x = (Functional) b;
		final Vector c = x.get(a);
		Assert.assertTrue(c.equals(RealLine.getInstance().getOne()));
		System.out.println(c.toString());
	}

}
