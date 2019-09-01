package definitions.structures.generic.finitedimensional.defs.spaces.impl;

import org.junit.Test;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class CachingLoadTest {
	
	final static int dimC=2;
	final static int dimF=5;
	
	final static Field realSpace = RealLine.getInstance();

	@Test
	public void loadCoordinateSpacesTest() throws Throwable {
		IGenerator gen = Generator.getGenerator();
		gen.loadCoordinateSpaces();
		EuclideanSpace plane=gen.getFiniteDimensionalVectorSpace(dimC);
	}
	
	@Test
	public void loadFunctionSpacesTest() throws Throwable {
		IGenerator gen = Generator.getGenerator();
		gen.loadFunctionSpaces();
		EuclideanFunctionSpace trig=gen.getSpacegenerator().getPolynomialFunctionSpace(realSpace, dimF, 1, true);
	}

}
