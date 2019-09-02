package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class CachingLoadTest {

	final static int dimC = 2;
	final static int dimF = 5;

	final static Field realSpace = RealLine.getInstance();

	@Test
	public void loadCoordinateSpacesTest() throws Throwable {
		final int k = 0;
		final IGenerator gen = Generator.getGenerator();
		gen.loadCoordinateSpaces();
		final int i = 0;
		final EuclideanSpace plane = gen.getFiniteDimensionalVectorSpace(dimC);
		final int j = 0;
	}

	@Test
	public void loadFunctionSpacesTest() throws Throwable {
		final IGenerator gen = Generator.getGenerator();
		gen.loadFunctionSpaces();
		final int j = 0;
		final EuclideanFunctionSpace trig = gen.getSpacegenerator().getPolynomialFunctionSpace(realSpace, dimF, 1,
				true);
		final int i = 0;
	}

}
