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
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.FunctionTuple;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;
import exceptions.WrongClassException;

public class TrigonometricSpaceWithLinearGrowthTest {

	static EuclideanFunctionSpace trigonometricFunctionSpace;
	static EuclideanFunctionSpace extendedTrigonometricFunctionSpace;

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

	static int dim = 3;

	@BeforeClass
	public static void setUpBeforeClass() {

		trigonometricFunctionSpace = Generator.getGenerator().getSpacegenerator().getTrigonometricSpace(dim);

		identity = new GenericFunction() {
			@Override
			public double value(double input) {
				return input;
			}
		};

		final Function idToFourier = new FunctionTuple(identity.getCoordinates(trigonometricFunctionSpace));

		final Function newBaseFunction = new GenericFunction() {

			private final Function normedIdToFourier = (Function) trigonometricFunctionSpace.normalize(idToFourier);

			private final double factor = trigonometricFunctionSpace.innerProduct(identity, this.normedIdToFourier);

			@Override
			public double value(double input) {
				final double ans = identity.value(input) - (this.factor * this.normedIdToFourier.value(input));
				return ans;
			}
		};

		final double[] baseVec = new double[(2 * dim) + 2];
		baseVec[(2 * dim) + 1] = 1;

		try {
			extendedTrigonometricFunctionSpace = SpaceGenerator.getInstance()
					.getTrigonometricFunctionSpaceWithLinearGrowth(dim);
		} catch (final WrongClassException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void test() {

		try {
			testValues = readFile(PATH);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		staircaseFunction = new GenericFunction() {
			int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public double value(double input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
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
	public void test2() {

		try {
			testValues2 = readFile(PATH2);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		staircaseFunction2 = new GenericFunction() {
			int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public double value(double input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues2[0][k] < l) {
					k++;
				}
				return testValues2[1][k];
			}

		};

		staircaseFunction2ToFourier = extendedTrigonometricFunctionSpace.getCoordinates(staircaseFunction2);

		final int length = (int) testValues2[0][testValues2[0].length - 1];

		staircaseFunction3 = new GenericFunction() {
			@Override
			public double value(double input) {
				final double newx = -Math.PI + ((2 * Math.PI * input) / length);
				return staircaseFunction2.value(newx);
			}
		};

		staircaseFunction3ToFourier = new GenericFunction() {
			@Override
			public double value(double input) {
				final double newx = -Math.PI + ((2 * Math.PI * input) / length);
				return ((Function) staircaseFunction2ToFourier).value(newx);
			}
		};

		staircaseFunction3.plotCompare(0, length, (Function) staircaseFunction3ToFourier);

		String ans = "";

		for (final Entry<Vector, Double> entry : staircaseFunction2ToFourier.getCoordinates().entrySet()) {
			ans += entry.toString() + "\r";
		}

	}

	@Test
	public void test3() {

		identityToFourier = extendedTrigonometricFunctionSpace.getCoordinates(identity);

		identity.plotCompare(left, right, (Function) identityToFourier);

	}

	@Test
	public void test4() {

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
	public void test5() {

		final List<Vector> base = extendedTrigonometricFunctionSpace.genericBaseToList();
		final double[][] scalarProducts = new double[base.size()][base.size()];
		int i = 0;
		String str = "";
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				scalarProducts[i][j] = extendedTrigonometricFunctionSpace.innerProduct(vec1, vec2);
				str += (scalarProducts[i][j] - (scalarProducts[i][j] % 0.001)) + ",";
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
			final String[] tmpdate = parts[0].split("-");
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
		br.close();
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
		for (final double entry : randomVar) {
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
