package subspaces.functionalspaces;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Vector;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.spaces.impl.SpaceGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.FunctionTuple;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.GenericFunction;

public class TrigonometricSpaceWithLinearGrowthTest {

	static IFiniteDimensionalFunctionSpace trigonometricFunctionSpace;
	static IFiniteDimensionalFunctionSpace extendedTrigonometricFunctionSpace;

	protected static final String PATH = "src/main/resources/test.csv";
	protected static final String PATH2 = "src/main/resources/test2.csv";

	protected static double[][] testValues;
	protected static double[][] testValues2;

	static Function staircaseFunction;
	static Function staircaseFunction2;
	static Function staircaseFunction3;
	static Function identity;

	static Vector staircaseFunctionToFourier;
	static Vector staircaseFunction2ToFourier;
	static Vector staircaseFunction3ToFourier;
	static Vector identityToFourier;

	static double left = -Math.PI;
	static double right = Math.PI;

	static int dim = 4;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		trigonometricFunctionSpace = Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(dim);

		identity = new GenericFunction() {
			@Override
			public double value(double input) {
				return input;
			}
		};

		Function idToFourier = new FunctionTuple(identity.getCoordinates(trigonometricFunctionSpace));

		Function newBaseFunction = new GenericFunction() {

			private final Function normedIdToFourier = (Function) trigonometricFunctionSpace.normalize(idToFourier);

			private final double factor = trigonometricFunctionSpace.product(identity, normedIdToFourier);

			@Override
			public double value(double input) throws Throwable {
				double ans = identity.value(input) - factor * normedIdToFourier.value(input);
				return ans;
			}

		};

		double[] baseVec = new double[2 * dim + 2];
		baseVec[2 * dim + 1] = 1;

		Function normedNewBaseFunction = new FunctionTuple(baseVec) {

			final double norm = trigonometricFunctionSpace.norm(newBaseFunction);

			@Override
			public double value(double input) throws Throwable {
				return newBaseFunction.value(input) / norm;
			}

			@Override
			public String toString() {
				return "orthonormal projection of identity: ";
			}

		};

		extendedTrigonometricFunctionSpace = SpaceGenerator.getInstance()
				.getTrigonometricFunctionSpaceWithLinearGrowth(dim, normedNewBaseFunction);

	}

	@Test
	public void test() throws Throwable {

		testValues = readFile(PATH);

		staircaseFunction = new GenericFunction() {
			int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public double value(double input) {
				double newInput = (length / (2 * Math.PI)) * input + length / 2.;
				int k = 0;
				int l = (int) (newInput - newInput % 1);
				while (testValues[0][k] < l) {
					k++;
				}
				return testValues[1][k];
			}
		};

		staircaseFunctionToFourier = extendedTrigonometricFunctionSpace.getCoordinates(staircaseFunction);

		staircaseFunction.plotCompare(left, right, (Function) staircaseFunctionToFourier);

	}

	@Test
	public void test2() throws Throwable {

		testValues2 = readFile(PATH2);

		staircaseFunction2 = new GenericFunction() {
			int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public double value(double input) {
				double newInput = (length / (2 * Math.PI)) * input + length / 2.;
				int k = 0;
				int l = (int) (newInput - newInput % 1);
				while (testValues2[0][k] < l) {
					k++;
				}
				return testValues2[1][k];
			}

		};

		staircaseFunction2ToFourier = extendedTrigonometricFunctionSpace.getCoordinates(staircaseFunction2);

		int length = (int) testValues2[0][testValues2[0].length - 1];

		staircaseFunction3 = new GenericFunction() {
			@Override
			public double value(double input) throws Throwable {
				double newx = -Math.PI + 2 * Math.PI * input / length;
				return staircaseFunction2.value(newx);
			}
		};

		staircaseFunction3ToFourier = new GenericFunction() {
			@Override
			public double value(double input) throws Throwable {
				double newx = -Math.PI + 2 * Math.PI * input / length;
				return ((Function) staircaseFunction2ToFourier).value(newx);
			}
		};

		staircaseFunction3.plotCompare(0, length, (Function) staircaseFunction3ToFourier);

		String ans = "";

		for (Entry<Vector, Double> entry : staircaseFunction2ToFourier.getCoordinates().entrySet()) {
			ans += entry.toString() + "\r";
		}

	}

	@Test
	public void test3() throws Throwable {

		identityToFourier = extendedTrigonometricFunctionSpace.getCoordinates(identity);

		identity.plotCompare(left, right, (Function) identityToFourier);

	}

	@Test
	public void test4() throws Throwable {

		final Function exp = new GenericFunction() {
			@Override
			public double value(double input) {
				return Math.exp(input);
			}
		};
		final Function ans = (Function) extendedTrigonometricFunctionSpace.getCoordinates(exp);
		exp.plotCompare(left, right, ans);

	}

	@Test
	public void test5() throws Throwable {

		List<Vector> base = extendedTrigonometricFunctionSpace.genericBaseToList();
		double[][] scalarProducts = new double[base.size()][base.size()];
		int i = 0;
		String str = "";
		for (Vector vec1 : base) {
			int j = 0;
			for (Vector vec2 : base) {
				scalarProducts[i][j] = extendedTrigonometricFunctionSpace.product(vec1, vec2);
				str += (scalarProducts[i][j] - scalarProducts[i][j] % 0.001) + ",";
				j++;
			}
			str += "\r";
			i++;
		}
		System.out.println(str);
	}

	protected static double[][] readFile(String string) throws IOException {
		final List<double[]> values = new ArrayList<>();
		final BufferedReader br = new BufferedReader(new FileReader(string));
		String line = "";
		LocalDate firstDate = null;
		while ((line = br.readLine()) != null) {
				final String[] parts = line.split(";");
				String[] tmpdate = parts[0].split("-");
				final LocalDate date = LocalDate.of(Integer.parseInt(tmpdate[0]), Integer.parseInt(tmpdate[1]),
						Integer.parseInt(tmpdate[2]));
				if (firstDate == null) {
					firstDate = date;
				}
				final double[] tmp = new double[2];
				tmp[0] = firstDate.until(date, ChronoUnit.DAYS);
				tmp[1] = Double.parseDouble(parts[1]);
				values.add(tmp);
			}
		final double[][] ans = new double[2][values.size()];
		for (int i = 0; i < values.size(); i++) {
			ans[0][i] = values.get(i)[0];
			ans[1][i] = values.get(i)[1];
		}
		return ans;
	}

	protected double correlationCoefficient(double[] measured, double[] produced) {

		return 0;
	}

	protected double covariance(double[] randomVar1, double[] randomVar2) {

		return 0;
	}

	protected double expectedValue(double[] randomVar) {
		double ans = 0;
		for (double entry : randomVar) {
			ans += entry;
		}
		return ans / randomVar.length;
	}

	protected Function standardDeviation() {
		return null;
	}

	protected double mean() {
		return 0;
	}

}
