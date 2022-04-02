package definitions.aspectjtest;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real; 
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

/**
 * 
 * @author roman
 *
 */
public class TrigonometricSobolevSpaceWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	private int sobolevDegree = 1;
	private final int trigDegree = 15;

	public int getSobolevDegree() {
		return sobolevDegree;
	}

	private void setSobolevDegree(final int degree) {
		sobolevDegree = degree;
	}

	@Override
	@Before
	public void setUp() throws Exception {

		setTrigonometricDegree(trigDegree);
		setSobolevDegree(sobolevDegree);

		setField(AspectJTest.getRealLine());
		super.setUp();
		setTrigonometricSpace(AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(AspectJTest.getRealLine(),
				sobolevDegree,getTrigonometricDegree(), trigDegree));

	}

	@Test
	public void test() {
		final Function absolute = new GenericFunction() { 
			private static final long serialVersionUID = -5009775881103765610L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) { 
				return input;
			} 
		};
		final Function projectionOfAbsolute = absolute.getProjection(getTrigonometricSpace());
//		projectionOfAbsolute.plotCompare(-Math.PI, Math.PI, absolute);
		
		EuclideanSpace L2 = AspectJTest.getSpaceGenerator().getTrigonometricSpace(
				AspectJTest.getRealLine(),getTrigonometricDegree(),Math.PI);
		final Function projectionOfAbsolute2 = absolute.getProjection(L2);
		projectionOfAbsolute2.plotCompare(-Math.PI, Math.PI, projectionOfAbsolute);
	}

	@Test
	public void test1() {
		final Function absolute = new GenericFunction() { 
			private static final long serialVersionUID = -5009775881103765610L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) { 
				return input;
			}

		};
		final Function projectionOfAbsolute = absolute.getProjection(getTrigonometricSpace());
		projectionOfAbsolute.plotCompare(-Math.PI, Math.PI, absolute);
	}

	@Test
	public void test2() {
		final Function staircaseFunction1Projection = getStaircaseFunction().getProjection(getTrigonometricSpace());
		getStaircaseFunction().plotCompare(-Math.PI, Math.PI, staircaseFunction1Projection);
	}
}
