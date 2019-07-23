package subspaces.functionalspaces;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.GenericFunction;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;
import definitions.structures.finitedimensional.real.vectorspaces.impl.SpaceGenerator;

public class PolynomialRegressionTest {

	final static int maxDegree = 2;
	final static int trigonometricDegree = 15;
	final static double left = -1;
	final static double right = 1;
	final static int degree = 2;

	static VectorSpace polynomialSpace;
	static VectorSpace trigonometricSpace;

	static Function exp = new GenericFunction() {
		@Override
		public double value(double input) {
			return Math.exp(input);
		}
	};
	static Function staircaseFunction;
	static Function staircaseFunction2;
	protected static final String PATH2 = "src/main/resources/test.csv";

	protected static double[][] testValues2;

	@BeforeClass
	public static void before() throws Throwable {
		testValues2 = readFile(PATH2);
		polynomialSpace = SpaceGenerator.getInstance().getPolynomialSobolevSpace(maxDegree, right, degree);
//		polynomialSpace = SpaceGenerator.getInstance().getPolynomialFunctionSpace(maxDegree, left, right);
//		trigonometricSpace = SpaceGenerator.getInstance()
//				.getTrigonometricFunctionSpaceWithLinearGrowth(trigonometricDegree, 1);
		trigonometricSpace = SpaceGenerator.getInstance()
				.getTrigonometricSobolevSpace(trigonometricDegree, 1);
		staircaseFunction = new GenericFunction() {
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
		staircaseFunction2 = staircaseFunction.getProjection((EuclideanSpace) trigonometricSpace);

	}

	@Test
	public void test1() throws Throwable {
		final Function coordinates = exp.getProjection((EuclideanSpace) polynomialSpace);
		coordinates.plotCompare(left, right, exp);
	}

	@Test
	public void test2() throws Throwable {
		final Function coordinates = staircaseFunction2.getProjection((EuclideanSpace) polynomialSpace);
		coordinates.plotCompare(left, right, staircaseFunction);
	}

	@Test
	public void test() throws Throwable {
		staircaseFunction2.plotCompare(left, right, staircaseFunction);
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

	@Test
	public void test3() {
		((EuclideanSpace) trigonometricSpace).show();
	}
}
