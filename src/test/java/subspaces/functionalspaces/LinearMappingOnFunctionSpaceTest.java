package subspaces.functionalspaces;

import java.util.Map;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class LinearMappingOnFunctionSpaceTest {

	private static LinearMapping map;
	private static LinearMapping map2;

	private static EuclideanSpace space;
	private static Function sin;
	private static Function cos;
	private static Function sin2;
	private static Homomorphism composition;
	private static Function fun;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		space = Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(1);

		map = new LinearMapping(space, space) {
			@Override
			public Vector get(Vector vec2) throws Throwable {
				return new GenericFunction() {
					@Override
					public double value(double input) throws Throwable {
						return ((Function) vec2).value(input + Math.PI / 2.);
					}
				};
			}
			@Override
			public Map<Vector, Double> getLinearity(Vector vec1) throws Throwable {
				return null;
			}
			
		};
		map2 = new LinearMapping(space, space) {
			@Override
			public Vector get(Vector vec2) throws Throwable {
				return new GenericFunction() {
					@Override
					public double value(double input) throws Throwable {
						return ((Function) vec2).value(input + Math.PI);
					}
				};
			}
			@Override
			public Map<Vector, Double> getLinearity(Vector vec1) throws Throwable {
				return null;
			}
		};

		sin = (Function) space.genericBaseToList().get(1);
		cos = (Function) map.get(sin);

		sin2 = (Function) map2.get(sin);

		composition = (Generator.getGenerator().getMappinggenerator().getComposition(map2, map));
		fun = (Function) composition.get(space.genericBaseToList().get(1));

	}

	@Test
	public void test() throws Throwable {
		runTest(cos, sin);
	}

	@Test
	public void test2() throws Throwable {
		runTest(sin2, sin);
	}

	@Test
	public void test3() throws Throwable {
		runTest(fun, cos);
	}

	private void runTest(Function function, Function testFun) throws Throwable {
		function.plotCompare(-Math.PI, Math.PI, testFun);
		for (Entry<Vector, Double> vec : function.getCoordinates(space).entrySet()) {
			System.out.println(vec.getKey().toString() + ": " + vec.getValue().toString());
		}
	}

}
