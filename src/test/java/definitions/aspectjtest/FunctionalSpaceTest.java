/**
 *
 */
package definitions.aspectjtest;

import org.junit.Test;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.semigroups.Element;
import definitions.structures.abstr.mappings.Functional;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

/**
 * @author ro
 *
 */
public class FunctionalSpaceTest {

	final int dim=3;
	final int  sobOrder=2;
	final EuclideanSpace space = ((SpringConfiguration) SpringConfiguration.getSpringConfiguration())
			.getApplicationContext().getBean(SpaceGenerator.class).getFiniteDimensionalVectorSpace(dim);
	final EuclideanSpace dualSpace = space.getDualSpace();
	final EuclideanSpace dualDualSpace = dualSpace.getDualSpace();
	final EuclideanSpace functionSpace = SpaceGenerator.getInstance().getNormedTrigonometricSpace(RealLine.getInstance(), 1);
	final EuclideanSpace dualFunctionSpace = functionSpace.getDualSpace();
	final EuclideanSpace dualDualFunctionSpace = dualFunctionSpace.getDualSpace();
	final EuclideanSpace sobolevSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace(RealLine.getInstance(), dim, sobOrder);
	final EuclideanSpace dualSobolevSpace = sobolevSpace.getDualSpace();

	final EuclideanSpace dualDualSobolevSpace = dualSobolevSpace.getDualSpace();

	public void test(EuclideanSpace space) {
		for( Vector a : space.genericBaseToList()) {
			for(Vector b : space.getDualSpace().genericBaseToList()) {
				final Functional x = (Functional) b;
				final Element c = x.get(a);
				System.out.print(c.toString()+"  ");}
			System.out.println();}
	}

	public void testDual(EuclideanSpace space) {
		for( Vector a : space.genericBaseToList()) {
			final Functional x = (Functional) a;
			for(Vector b : space.getDualSpace().genericBaseToList()) {
				final Element c = x.get(b);
				System.out.print(c.toString()+"  ");}
			System.out.println();}
	}

	@Test
	public void spaceTest() {
		test(space);
	}

	@Test
	public void functionSpaceTest() {
		test(functionSpace);
	}

	@Test
	public void sobolevSpaceTest() {
		test(sobolevSpace);
	}


	@Test
	public void spaceDualDualTest() {
		testDual(dualFunctionSpace);
	}

	@Test
	public void spaceSobolevDualDualTest() {
		testDual(dualSobolevSpace);
	}

}
