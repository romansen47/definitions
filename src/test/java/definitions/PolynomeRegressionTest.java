package definitions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.impl.Monome;
import exceptions.DevisionByZeroException;

public class PolynomeRegressionTest extends AspectJTest {

	// for smaller intervalls we do get greater distances
	private static final double left = -Math.PI;
	private static final double right = -left;

	private static List<Vector> base = new ArrayList<>();

	private static Function sin = null;
	private static Function exp = null;

	static EuclideanFunctionSpace space = null;

	private final double toleranceExp = 1.5;
	private final double toleranceSine = 0.75;

	private static final int polynomialDegree = 4;

	protected static final Field realLine = ((SpringConfiguration) getSpringConfiguration()).getApplicationContext()
			.getBean(RealLine.class);

	@BeforeClass
	public static void setUpBeforeClass() throws DevisionByZeroException {

		sin = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

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

		for (int i = 0; i < (polynomialDegree + 1); i++) {
			base.add(new Monome(i) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Field getField() {
					return realLine;
				}
			});
		}

		space = (EuclideanFunctionSpace) Generator.getInstance().getFiniteDimensionalFunctionSpace(realLine, base, left,
				right);

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
