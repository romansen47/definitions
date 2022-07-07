package definitions.aspectjtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

public class TrigonometricSpaceTestWithLinearGrowthTest extends GenericTrigonometricSpaceTest {

	public static final Logger logger = LogManager.getLogger(TrigonometricSpaceTestWithLinearGrowthTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	private final int trigDegree = 19;

	@Override
	@Before
	public void setUp() throws Exception {

		setTrigonometricDegree(trigDegree);

		setField(AspectJTest.getRealLine());
		super.setUp();
		setTrigonometricSpace(AspectJTest.getSpaceGenerator()
				.getTrigonometricFunctionSpaceWithLinearGrowth(AspectJTest.getRealLine(), trigDegree, Math.PI));

	}

	@Test
	public void testOnStaircaseFunction() {
		testOnFunction(getStaircaseFunction(), trigDegree, null, 1d);
	}

	@Test
	public void testOnAbsolute() {
		final Function absolute = new GenericFunction() {
			private static final long serialVersionUID = -5009775881103765610L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}

			@Override
			public Scalar value(Scalar input) {
				return getRealLine().get(Math.abs(((Real) input).getRepresentant()));
			}
		};

		testOnFunction(absolute, trigDegree, null, 1d);
	}

	@Test
	public void testOnIdentity() {
		final Function identity = new GenericFunction() {
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
		testOnFunction(identity, trigDegree, null, 1e-3);
	}

}
