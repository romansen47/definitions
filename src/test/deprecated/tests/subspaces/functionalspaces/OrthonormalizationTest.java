package tests.subspaces.functionalspaces;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.algebra.fields.impl.RealLine;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class OrthonormalizationTest {

	static EuclideanSpace space;

	static EuclideanSpace space2;
	static Vector a;

	static Vector b;
	static Vector c;
	static double ans1;

	static double ans2;
	static double ans3;
	static double ans4;
	static double ans5;
	static double ans6;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		space = Generator.getInstance().getSpaceGenerator().getTrigonometricSpace(RealLine.getInstance(), 1);

		final List<Vector> genericBase = space.genericBaseToList();
		final List<Vector> system = new ArrayList<>();

		final Vector x1 = space.addition(genericBase.get(0), genericBase.get(1));
		final Vector x2 = space.addition(genericBase.get(1), genericBase.get(2));
		final Vector x3 = space.addition(genericBase.get(2),
				space.stretch(genericBase.get(0), RealLine.getInstance().get(2)));

		system.add(x1);
		system.add(x2);
		system.add(x3);

		final List<Vector> newBase = space.getOrthonormalBase(system);

		a = newBase.get(0);
		b = newBase.get(1);
		c = newBase.get(2);

		ans1 = space.innerProduct(a, b).getDoubleValue();
		ans2 = space.innerProduct(b, c).getDoubleValue();
		ans3 = space.innerProduct(c, a).getDoubleValue();

		ans4 = space.innerProduct(a, a).getDoubleValue();
		ans5 = space.innerProduct(b, b).getDoubleValue();
		ans6 = space.innerProduct(c, c).getDoubleValue();

	}

	final double eps = 1.e-3;

	@Test
	public void exponential() throws Throwable {
		final Vector exp = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(final Scalar input) {
				return RealLine.getInstance().get(Math.exp(input.getDoubleValue() + Math.exp(-input.getDoubleValue())));
			}
		};
		final Vector x = space.getCoordinates(exp);
		final double y = (Math.exp(Math.PI) - Math.exp(-Math.PI)) / Math.sqrt(2 * Math.PI);
		// Assert.assertTrue((Math.abs(x.getCoordinates()[0].getValue() - y) < this.eps)
		// || (Math.abs(x.getCoordinates()[1].getValue() - y) < this.eps)
		// || (Math.abs(x.getCoordinates()[2].getValue() - y) < this.eps));
	}

	@Test
	public void normalized() throws Throwable {
		Assert.assertTrue(Math.abs(space.norm(a).getDoubleValue() - 1) < this.eps);
		Assert.assertTrue(Math.abs(space.norm(b).getDoubleValue() - 1) < this.eps);
		Assert.assertTrue(Math.abs(space.norm(c).getDoubleValue() - 1) < this.eps);
	}

	@Test
	public void orthogonal() throws Throwable {
		Assert.assertTrue(Math.abs(ans1) < this.eps);
		Assert.assertTrue(Math.abs(ans2) < this.eps);
		Assert.assertTrue(Math.abs(ans3) < this.eps);
	}
}
