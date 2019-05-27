package definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import junit.framework.Assert;

public class PerformanceTest {

	final static int max=15;
	
	static IFiniteDimensionalFunctionSpace space;

	static Function identity;
	static Function abs;
	
	@BeforeClass
	public static void prepare() throws Throwable {
		space=SpaceGenerator.getInstance() .getTrigonometricSpace(max);
		identity=new FunctionTuple(
				SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(1).
						genericBaseToList().get(0).getGenericCoordinates()) {
			@Override
			public double value(double input) {
				return input;
			}
		};
		abs=new FunctionTuple(
				SpaceGenerator.getInstance().getFiniteDimensionalVectorSpace(1).
						genericBaseToList().get(0).getGenericCoordinates()) {
			@Override
			public double value(double input) {
				return Math.abs(input);
			}
		};
	}
	
	@Test
	public void identity() throws Throwable {	
		Vector toFourier=space.getCoordinates(identity);
		//((Function)toFourier).plot(-Math.PI,Math.PI);
		identity.plotCompare(-Math.PI,Math.PI,((Function)toFourier));
		Assert.assertTrue(true);
	}
	
	@Test
	public void abs() throws Throwable {	
		Vector toFourier=space.getCoordinates(abs);
		//((Function)toFourier).plot(-Math.PI,Math.PI);
		abs.plotCompare(-Math.PI,Math.PI,((Function)toFourier));
		double ans=space.getDistance(toFourier,identity);
		Assert.assertTrue(true);
	}

}
