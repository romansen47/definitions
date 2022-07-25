package definitions.prototypes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.SpringConfiguration;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.vectors.impl.GenericFunction;

/**
 * example for polynomial regression. extensions by linear functions must be
 * avoided (since they make no sense)
 * 
 * @author ro
 *
 */
public abstract class GenericPolynomeRegressionTest extends GenericSpaceTest {

	public static final Logger logger = LogManager.getLogger(GenericPolynomeRegressionTest.class);

	@Override
	public Logger getLogger() {
		return GenericPolynomeRegressionTest.logger;
	}

	// for smaller intervalls we do get greater distances
	protected double left = -Math.PI;
	protected double right = -left;

	private Function sin;
	private Function exp;

	private double toleranceExp = 1.5;
	private double toleranceSine = 0.75;

	protected static final Field realLine = ((SpringConfiguration) getSpringConfiguration()).getApplicationContext()
			.getBean(RealLine.class);

	@Before
	@Override
	public void setUp() throws Exception {

		sin = new GenericFunction() {

			@Override
			public Field getField() {
				return realLine;
			}

			@Override
			public Scalar value(final Scalar input) {
				return RealLine.getInstance().get(Math.sin(((Real) input).getRepresentant() * Math.PI / right));
			}
		};

		exp = new GenericFunction() {
			@Override
			public Field getField() {
				return realLine;
			}

			@Override
			public Scalar value(final Scalar input) {
				Scalar x = (Scalar) realLine.product((Vector) input, (Vector) realLine.get(1));
				return realLine.get(Math.exp(((Real) x).getRepresentant()));
			}
		};

		super.setUp();
	}

	@Test
	public void regressionExpByPolynomialsTest() throws Throwable {
		final Function ans = exp.getProjection(space);
		exp.plotCompare(left, right, ans);
		double distance = ((Real) space.distance(ans, exp)).getRepresentant();
		logger.info("distance to exp is {}", distance);
		Assert.assertTrue(distance < toleranceExp);
	}

	@Test
	public void regressionSinByPolynomialsTest() throws Throwable {
		final Function ans = sin.getProjection(space);
		ans.plotCompare(left, right, sin);
		double distance = ((Real) space.distance(ans, sin)).getRepresentant();
		logger.info("distance to sin is {}", distance);
		Assert.assertTrue(distance < toleranceSine);
	}

}
