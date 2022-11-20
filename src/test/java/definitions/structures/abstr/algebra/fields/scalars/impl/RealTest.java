package definitions.structures.abstr.algebra.fields.scalars.impl;

import java.util.Map;
import java.util.Random;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.impl.GenericTest;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;

public class RealTest extends GenericTest {

	private final double eps = 1e-7;

	final Real zero = getRealLine().getZero();
	final Real one = getRealLine().getOne();

	final private double value = new Random().nextDouble();

	private final Real real = getRealLine().get(value);

	@Test
	public void testGetRepresentant() {
		Assert.assertEquals(real.getRepresentant(), value, eps);
	}

	@Test
	public void testDoubleValue() {
		Assert.assertEquals(real.doubleValue(), value, eps);
	}

	@Test
	public void testEquals() {
		Assert.assertNotEquals(real, zero);
		Assert.assertNotEquals(real, one);
		Assert.assertTrue(real.equals(getRealLine().get(value)));
	}

	@Test
	public void testFloatValue() {
		Assert.assertEquals(real.floatValue(), value, eps);
	}

	@Test
	public void testGetCoordinates() {
		Map<Vector, Scalar> coordinates = real.getCoordinates();
		Assert.assertEquals(coordinates.entrySet().size(), 1, eps);
		Assert.assertEquals(((Real) coordinates.get(one)).doubleValue(), value, eps);

		coordinates = real.getCoordinates(getRealLine());
		Assert.assertEquals(coordinates.entrySet().size(), 1, eps);
		Assert.assertEquals(((Real) coordinates.get(one)).doubleValue(), value, eps);
	}

	@Test
	public void testGetDim() {
		Assert.assertEquals(1, real.getDim(), eps);
	}

	@Test
	public void testElementOf() {
		Assert.assertTrue(real.elementOf(getRealLine()));
	}

}
