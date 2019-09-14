package subspaces.functionalspaces;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class OrthonormalizationTest {

	final double eps = 1.e-3;

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

		space = Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(RealLine.getInstance(), 1);

		final List<Vector> genericBase = space.genericBaseToList();
		final List<Vector> system = new ArrayList<>();

		final Vector x1 = space.add(genericBase.get(0), genericBase.get(1));
		final Vector x2 = space.add(genericBase.get(1), genericBase.get(2));
		final Vector x3 = space.add(genericBase.get(2), space.stretch(genericBase.get(0), new Real(2)));

		system.add(x1);
		system.add(x2);
		system.add(x3);

		final List<Vector> newBase = space.getOrthonormalBase(system);

		a = newBase.get(0);
		b = newBase.get(1);
		c = newBase.get(2);

		ans1 = space.innerProduct(a, b).getValue();
		ans2 = space.innerProduct(b, c).getValue();
		ans3 = space.innerProduct(c, a).getValue();

		ans4 = space.innerProduct(a, a).getValue();
		ans5 = space.innerProduct(b, b).getValue();
		ans6 = space.innerProduct(c, c).getValue();

	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void orthogonal() throws Throwable {
		Assert.assertTrue(Math.abs(ans1) < this.eps);
		Assert.assertTrue(Math.abs(ans2) < this.eps);
		Assert.assertTrue(Math.abs(ans3) < this.eps);
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void normalized() throws Throwable {
		Assert.assertTrue(Math.abs(space.norm(a).getValue() - 1) < this.eps);
		Assert.assertTrue(Math.abs(space.norm(b).getValue() - 1) < this.eps);
		Assert.assertTrue(Math.abs(space.norm(c).getValue() - 1) < this.eps);
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void exponential() throws Throwable {
		final Vector exp = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(Scalar input) {
				return new Real(Math.exp(input.getValue() + Math.exp(-input.getValue())));
			}
		};
		final Vector x = space.getCoordinates(exp);
		final double y = (Math.exp(Math.PI) - Math.exp(-Math.PI)) / Math.sqrt(2 * Math.PI);
		// Assert.assertTrue((Math.abs(x.getCoordinates()[0].getValue() - y) < this.eps)
		// || (Math.abs(x.getCoordinates()[1].getValue() - y) < this.eps)
		// || (Math.abs(x.getCoordinates()[2].getValue() - y) < this.eps));
	}
}
