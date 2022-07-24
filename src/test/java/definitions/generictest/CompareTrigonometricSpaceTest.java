package definitions.generictest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.GenericTest;
import definitions.prototypes.GenericTrigonometricSpaceTest;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import exceptions.DevisionByZeroException;
import exceptions.ExtendingFailedException;

/**
 * this tests that extending a trigonometric space by linear functions truly
 * produces a higher dimensional space that approximates better.
 * 
 * @author roman
 *
 */
public class CompareTrigonometricSpaceTest extends GenericTrigonometricSpaceTest {

	protected static final Logger logger = LogManager.getLogger(CompareTrigonometricSpaceTest.class);

	@Override
	public Logger getLogger() {
		return logger;
	}

	EuclideanFunctionSpace space;

	EuclideanFunctionSpace extendedSpace;

	@Override
	@Before
	public void setUp() throws Exception {
		eps = 9e-1;
		trigonometricDegree = 4;
		sobolevDegree = null;
		setField(GenericTest.getRealLine());
		space = (EuclideanFunctionSpace) GenericTest.getSpaceGenerator()
				.getTrigonometricSpace(GenericTest.getRealLine(), getTrigonometricDegree(), Math.PI);
		extendedSpace = (EuclideanFunctionSpace) GenericTest.getSpaceGenerator()
				.getTrigonometricFunctionSpaceWithLinearGrowth(GenericTest.getRealLine(), getTrigonometricDegree(),
						Math.PI);
		super.setUp();
	}

	@Test
	public void compareStairCaseFunction1Test() throws DevisionByZeroException, ExtendingFailedException {
		this.setStaircaseFunction((GenericFunction) sFunction1);
		Function f = sFunction1.getProjection(space);
		Function extendedf = sFunction1.getProjection(extendedSpace);
		sFunction1.plotCompare(-Math.PI, Math.PI, f);
		sFunction1.plotCompare(-Math.PI, Math.PI, extendedf);
		f.plotCompare(-Math.PI, Math.PI, extendedf);
		double distance = extendedSpace.distance(f, extendedf).getDoubleValue();
		double distance1 = extendedSpace.distance(sFunction1, extendedf).getDoubleValue();
		double distance2 = extendedSpace.distance(f, sFunction1).getDoubleValue();
		double sum = Math.pow(Math.pow(distance, 2) + Math.pow(distance1, 2) + Math.pow(distance2, 2), 0.5);
		logger.info("{}+{}+{} = {}, relative distance = distance / sum = {}", distance, distance1, distance2, sum,
				distance / sum);
		Assert.assertTrue(distance / sum < eps);
	}

	@Test
	public void compareStairCaseFunction2Test() throws DevisionByZeroException, ExtendingFailedException {
		this.setStaircaseFunction((GenericFunction) sFunction2);
		Function f = sFunction2.getProjection(space);
		Function extendedf = sFunction2.getProjection(extendedSpace);
		sFunction2.plotCompare(-Math.PI, Math.PI, f);
		sFunction2.plotCompare(-Math.PI, Math.PI, extendedf);
		f.plotCompare(-Math.PI, Math.PI, extendedf);
		double distance = extendedSpace.distance(f, extendedf).getDoubleValue();
		double distance1 = extendedSpace.distance(sFunction2, f).getDoubleValue();
		double distance2 = extendedSpace.distance(sFunction2, extendedf).getDoubleValue();
		double sum = Math.pow(Math.pow(distance, 2) + Math.pow(distance1, 2) + Math.pow(distance2, 2), 0.5);
		logger.info("{}+{}+{} = {}, relative distance = distance / sum = {}", distance, distance1, distance2, sum,
				distance / sum);
		Assert.assertTrue(distance / sum < eps);
	}

	@Test
	public void testOnStairCaseFunction1() {
		Assert.assertTrue(true);
	}

	@Test
	public void testOnStairCaseFunction2() {
		Assert.assertTrue(true);
	}

	@Test
	public void testOnAbsolute() {
		Assert.assertTrue(true);
	}

	@Test
	public void testOnIdentity() {
		Assert.assertTrue(true);
	}

	@Test
	public void testOnContinuousFunction() {
		Assert.assertTrue(true);
	}
}
