/**
 *
 */
package definitions.aspectjtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

	public static final Logger logger = LogManager.getLogger(FunctionalSpaceTest.class);

	final int dim = 3;
	final int sobOrder = 2;
	final EuclideanSpace space = ((SpringConfiguration) SpringConfiguration.getSpringConfiguration())
			.getApplicationContext().getBean(SpaceGenerator.class).getFiniteDimensionalVectorSpace(dim);
	final EuclideanSpace dualSpace = space.getDualSpace();
	final EuclideanSpace dualDualSpace = dualSpace.getDualSpace();
	final EuclideanSpace functionSpace = SpaceGenerator.getInstance()
			.getNormedTrigonometricSpace(RealLine.getInstance(), 1);
	final EuclideanSpace dualFunctionSpace = functionSpace.getDualSpace();
	final EuclideanSpace dualDualFunctionSpace = dualFunctionSpace.getDualSpace();
	final EuclideanSpace sobolevSpace = SpaceGenerator.getInstance()
			.getTrigonometricSobolevSpace(RealLine.getInstance(), dim, sobOrder);
	final EuclideanSpace dualSobolevSpace = sobolevSpace.getDualSpace();

	final EuclideanSpace dualDualSobolevSpace = dualSobolevSpace.getDualSpace();

	public void testEuclideanSpace(EuclideanSpace space) {
		for (Vector a : space.genericBaseToList()) {
			String s = "";
			for (Vector b : space.getDualSpace().genericBaseToList()) {
				final Functional x = (Functional) b;
				final Element c = x.get(a);
				s += c.toString() + "  ";
			}
			logger.info(s);
		}
	}

	public void testDualSpace(EuclideanSpace space) {
		for (Vector a : space.genericBaseToList()) {
			final Functional x = (Functional) a;
			String s = "";
			for (Vector b : space.getDualSpace().genericBaseToList()) {
				final Element c = x.get(b);
				s += c.toString() + "  ";
			}
			logger.info(s);
		}
	}

	@Test
	public void spaceTest() {
		testEuclideanSpace(space);
	}

	@Test
	public void functionSpaceTest() {
		testEuclideanSpace(functionSpace);
	}

	@Test
	public void sobolevSpaceTest() {
		testEuclideanSpace(sobolevSpace);
	}

	@Test
	public void spaceDualDualTest() {
		testDualSpace(dualFunctionSpace);
	}

	@Test
	public void spaceSobolevDualDualTest() {
		testDualSpace(dualSobolevSpace);
	}

}
