package tests.subspaces.functionalspaces;

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

import definitions.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.Scalar;
import definitions.structures.abstr.vectorspaces.vectors.FiniteVectorMethods;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class TrigonometricSpaceWithLinearGrowthTest extends AspectJTest{

	static EuclideanSpace trigonometricFunctionSpace;
	static EuclideanSpace extendedTrigonometricFunctionSpace;

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

	static int dim = 49;

	protected static double[][] readFile(final String string) throws IOException {
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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		final IGenerator gen = Generator.getInstance();

		final ISpaceGenerator spaceGen = gen.getSpaceGenerator();

		trigonometricFunctionSpace = spaceGen.getTrigonometricSpace(realLine, dim);

		extendedTrigonometricFunctionSpace = spaceGen
				.getTrigonometricFunctionSpaceWithLinearGrowth(realLine, dim);

	}

	protected double correlationCoefficient(final double[] measured, final double[] produced) {

		return 0;
	}

	protected double covariance(final double[] randomVar1, final double[] randomVar2) {

		return 0;
	}

	protected double expectedValue(final double[] randomVar) {
		double ans = 0;
		for (final double entry : randomVar) {
			ans += entry;
		}
		return ans / randomVar.length;
	}

	protected double mean() {
		return 0;
	}

	protected Function standardDeviation() {
		return null;
	}

	@Test
	public void test() {

		try {
			testValues = readFile(PATH);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		staircaseFunction = new GenericFunction() {
			private static final long serialVersionUID = 1L;
			int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Scalar value(final Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getDoubleValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues[0][k] < l) {
					k++;
				}
				return realLine.get(testValues[1][k]);
			}

		};

		staircaseFunctionToFourier = extendedTrigonometricFunctionSpace.getCoordinates(staircaseFunction);

		((GenericFunction) staircaseFunction).plotCompare(left, right, (Function) staircaseFunctionToFourier);

	}

	@Test
	public void test2() {

		try {
			testValues2 = readFile(PATH2);
		} catch (final IOException e) {
			e.printStackTrace();
		}

		staircaseFunction2 = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public Scalar value(final Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getDoubleValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues2[0][k] < l) {
					k++;
				}
				return realLine.get(testValues2[1][k]);
			}

		};

		staircaseFunction2ToFourier = extendedTrigonometricFunctionSpace.getCoordinates(staircaseFunction2);

		final int length = (int) testValues2[0][testValues2[0].length - 1];

		staircaseFunction3 = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(final Scalar input) {
				final double newx = -Math.PI + ((2 * Math.PI * input.getDoubleValue()) / length);
				return staircaseFunction2.value(realLine.get(newx));
			}

		};

		staircaseFunction3ToFourier = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(final Scalar input) {
				final double newx = -Math.PI + ((2 * Math.PI * input.getDoubleValue()) / length);
				return ((Function) staircaseFunction2ToFourier).value(realLine.get(newx));
			}

		};

		((GenericFunction) staircaseFunction3).plotCompare(0, length, (Function) staircaseFunction3ToFourier);

		String ans = "";

		for (final Entry<Vector, Scalar> entry : ((FiniteVectorMethods) staircaseFunction2ToFourier).getCoordinates()
				.entrySet()) {
			ans += entry.toString() + "\r";
		}

	}

	@Test
	public void test3() {
		identity = new GenericFunction() {
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(final Scalar input) {
				return input;
			}

		};
		identityToFourier = extendedTrigonometricFunctionSpace.getCoordinates(identity);
		((GenericFunction) identity).plotCompare(left, right, (Function) identityToFourier);
	}

	@Test
	public void test4() {

		final Function exp = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(final Scalar input) {
				return realLine.get(Math.exp(input.getDoubleValue()));
			}

		};
		final Function ans = (Function) extendedTrigonometricFunctionSpace.getCoordinates(exp);
		((GenericFunction) exp).plotCompare(left, right, ans);

	}

	@Test
	public void test5() {

//		final List<Vector> base = extendedTrigonometricFunctionSpace.genericBaseToList();
//		final double[][] scalarProducts = new double[base.size()][base.size()];
//		int i = 0;
//		String str = "";
//		for (final Vector vec1 : base) {
//			int j = 0;
//			for (final Vector vec2 : base) {
//				scalarProducts[i][j] = extendedTrigonometricFunctionSpace.innerProduct(vec1, vec2).getValue();
//				str += (scalarProducts[i][j] - (scalarProducts[i][j] % 0.001)) + ",";
//				j++;
//			}
//			str += "\r";
//			i++;
//		}
//		System.out.println(str);
		extendedTrigonometricFunctionSpace.show();
	}

}
