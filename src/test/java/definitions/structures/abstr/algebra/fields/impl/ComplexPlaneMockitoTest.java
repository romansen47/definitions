package definitions.structures.abstr.algebra.fields.impl;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.algebra.fields.scalars.impl.Complex;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;

public class ComplexPlaneMockitoTest extends AspectJTest {

	ComplexPlane complexPlane = getComplexPlane();
	RealLine realLine = getRealLine();
	Map<Vector, VectorSpaceHomomorphism> multiplicationMatrix = complexPlane.getMultiplicationMatrix();
	Vector baseVector1 = complexPlane.genericBaseToList().get(0);
	Vector baseVector2 = complexPlane.genericBaseToList().get(1);
	VectorSpaceHomomorphism identity = multiplicationMatrix.get(baseVector1);
	VectorSpaceHomomorphism rotation = multiplicationMatrix.get(baseVector2);

	@Test
	public void complexTest() {
		Assert.assertTrue(complexPlane.complex().equals(complexPlane.getZero()));
	}

	@Test
	public void conjugateTest() {
		Assert.assertTrue(complexPlane.conjugate((Scalar) baseVector1).equals(baseVector1));
		Assert.assertTrue(complexPlane.conjugate((Scalar) baseVector2)
				.equals(complexPlane.product(complexPlane.getMinusOne(), baseVector2)));
	}

	@Test
	public void getTest() {
		for (double x = -10.; x < 10; x++) {
			Complex y = complexPlane.get(x);
			Assert.assertTrue(y.getImag().equals(realLine.getZero()));
			Assert.assertTrue(y.getReal().equals(realLine.get(x)));
		}
	}

	@Test
	public void getMultiplicationMatrixTest() {
		Assert.assertTrue(identity.get(baseVector1).equals(baseVector1));
		Assert.assertTrue(identity.get(baseVector2).equals(baseVector2));
		Assert.assertTrue(rotation.get(baseVector1).equals(baseVector2));
		Assert.assertTrue(rotation.get(baseVector2)
				.equals(complexPlane.multiplication(getComplexPlane().getMinusOne(), baseVector1)));
	}

	@Test
	public void getMultiplicativeInverseElementTest() {
		Complex y = complexPlane.getMultiplicativeInverseElement(complexPlane.get(2, 0));
		Assert.assertTrue(y.getReal().equals(realLine.get(0.5)));
		Assert.assertTrue(y.getImag().equals(realLine.getZero()));

		Complex z = complexPlane.getMultiplicativeInverseElement(complexPlane.getI());
		Assert.assertTrue(z.getReal().equals(realLine.getZero()));
		Assert.assertTrue(z.getImag().equals(realLine.get(-1)));

	}

	@Test
	public void getPrimeFieldTest() {
		Assert.assertTrue(
				complexPlane.getPrimeField().equals(Generator.getInstance().getGroupGenerator().getRationals()));
	}

}
