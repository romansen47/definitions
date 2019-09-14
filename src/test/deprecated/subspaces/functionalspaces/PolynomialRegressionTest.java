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

import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class PolynomialRegressionTest {

	final static int polynomialDegree = 3;
	final static int trigonometricDegree = 5;
	final static double left = -1;
	final static double right = 1;

	// @TODO: Derivatives don't work
	final static int sobolevDegree = 1;

	static VectorSpace polynomialSpace;
	static VectorSpace trigonometricSpace;

	static Function exp = new GenericFunction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public Scalar value(Scalar input) {
			return new Real(Math.exp(input.getValue()));
		}
	};

	static Function staircaseFunction;
	static Function staircaseFunction2;

	static Function measures;
	static Function measures2;

	protected static final String PATH = "src/main/resources/test.csv";
	protected static final String PATH2 = "src/main/resources/test2.csv";

	protected static double[][] testValues;
	protected static double[][] testValues2;

	@BeforeClass
	public static void before() throws Throwable {

		testValues = readFile(PATH);
		testValues2 = readFile(PATH2);

		polynomialSpace = SpaceGenerator.getInstance().getPolynomialSobolevSpace(RealLine.getInstance(),
				polynomialDegree, right, sobolevDegree);

		trigonometricSpace = SpaceGenerator.getInstance().getTrigonometricSobolevSpace(RealLine.getInstance(),
				trigonometricDegree, sobolevDegree);

		staircaseFunction = new GenericFunction() {
			/** 
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues[0][k] < l) {
					k++;
				}
				return new Real(testValues2[1][k]);
			}
		};
		staircaseFunction2 = staircaseFunction.getProjection((EuclideanSpace) trigonometricSpace);

		measures = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues2[0][k] < l) {
					k++;
				}
				return new Real(testValues2[1][k]);
			}
		};
		measures2 = measures.getProjection((EuclideanSpace) trigonometricSpace);
	}

	// @Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test1() throws Throwable {
		final Function coordinates = exp.getProjection((EuclideanSpace) polynomialSpace);
		coordinates.plotCompare(left, right, exp);
	}

	@Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test2() throws Throwable {
		final Function coordinates = staircaseFunction2.getProjection((EuclideanSpace) polynomialSpace);
		coordinates.plotCompare(left, right, staircaseFunction2);

		final Function coordinates2 = measures2.getProjection((EuclideanSpace) polynomialSpace);
		coordinates2.plotCompare(left, right, measures2);
	}

	// @Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
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

	// @Test@settings.Trace(trace = settings.GlobalSettings.LOGGING, depth = settings.GlobalSettings.LOGGING_DEPTH, initial = true, transit = true)
	public void test3() {
		((EuclideanSpace) trigonometricSpace).show();
	}
}
