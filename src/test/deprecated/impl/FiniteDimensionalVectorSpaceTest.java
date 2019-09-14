package impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class FiniteDimensionalVectorSpaceTest {

	static EuclideanSpace space;

	static Vector a;
	static Vector b;
	static Vector c;
	static Vector d;

	static double ans1;
	static double ans2;
	static double ans3;
	static double ans4;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		space = Generator.getGenerator().getSpacegenerator().getFiniteDimensionalVectorSpace(4);

		final List<Vector> genericBase = space.genericBaseToList();
		final List<Vector> system = new ArrayList<>();

		final Vector x1 = space.add(genericBase.get(0), genericBase.get(1));
		final Vector x2 = space.add(genericBase.get(1), genericBase.get(2));
		final Vector x3 = space.add(genericBase.get(2), genericBase.get(3));
		final Vector x4 = space.add(genericBase.get(3), space.stretch(genericBase.get(0), new Real(-1)));

		system.add(x1);
		system.add(x2);
		system.add(x3);
		system.add(x4);

		final List<Vector> newBase = space.getOrthonormalBase(system);

		a = newBase.get(0);
		b = newBase.get(1);
		c = newBase.get(2);
		d = newBase.get(3);

		ans1 = space.innerProduct(a, b).getValue();
		ans2 = space.innerProduct(b, c).getValue();
		ans3 = space.innerProduct(c, d).getValue();
		ans4 = space.innerProduct(a, d).getValue();

	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void orthogonal() throws Throwable {
		Assert.assertTrue(Math.abs(ans1) < 1.e-5);
		Assert.assertTrue(Math.abs(ans2) < 1.e-5);
		Assert.assertTrue(Math.abs(ans3) < 1.e-5);
		Assert.assertTrue(Math.abs(ans4) < 1.e-5);
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void normalized() throws Throwable {
		Assert.assertTrue(Math.abs(space.norm(a).getValue() - 1) < 1.e-5);
		Assert.assertTrue(Math.abs(space.norm(b).getValue() - 1) < 1.e-5);
		Assert.assertTrue(Math.abs(space.norm(c).getValue() - 1) < 1.e-5);
		Assert.assertTrue(Math.abs(space.norm(d).getValue() - 1) < 1.e-5);
	}

}
