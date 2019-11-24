package tests.subspaces.functionalspaces;

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
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.IGenerator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class FiniteDimensionalSobolevSpaceTest {
 
	final static VectorSpace realLine = RealLine.getInstance();
	static EuclideanSpace trigonometricFunctionSpace;
	static EuclideanSpace sobolevSpace;
	final static ISpaceGenerator spaceGen =  SpaceGenerator.getInstance();

	static double left = -Math.PI;
	static double right = Math.PI;

	static final int dim = 1;

	static final int degree = 5;

	static Function normalizedIdentity;
	static Function exp;

	protected static final String PATH = "src/main/resources/test.csv";
	protected static final String PATH2 = "src/main/resources/test2.csv";

	protected static double[][] testValues;
	protected static double[][] testValues2;

	static Vector niceOne;
	static Vector staircaseFunction;
	static Vector staircaseFunction2;

	static Vector newAbs;
	static Vector staircaseFunctionToFourier;
	static Vector staircaseFunction2ToFourier;
	static Function stairs;

	static Vector idToSobolevFourierCoordinates;

	static Vector expToSobolevFourierCoordinates;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		testValues = readFile(PATH);

		testValues2 = readFile(PATH2);

		normalizedIdentity = new GenericFunction() { 
			private static final long serialVersionUID = 1L;
			final double norm = Math.sqrt(2 * Math.PI) + Math.sqrt((2 * Math.pow(Math.PI, 3)) / 3);

			@Override
			public Scalar value(Scalar input) {
				return RealLine.getInstance().get(input.getValue() / this.norm);
			}

		};
		exp = new ExponentialFunction((Scalar) ((RealLine) realLine).nullVec(), ((RealLine) realLine).getOne()) {
 
			private static final long serialVersionUID = 1L;
		};
		niceOne = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(Scalar input) {
				double ans = input.getValue();
				double newAns = Math.sqrt(1 + Math.pow(ans, 2));
				return RealLine.getInstance().get(newAns);
			}
		};
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
				return RealLine.getInstance().get(testValues[1][k]);
			}

		};
		staircaseFunction2 = new GenericFunction() { 
			private static final long serialVersionUID = 1L;
			int length = (int) testValues2[0][testValues2[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (k < testValues2[0].length - 1 && testValues2[0][k + 1] < l) {
					k++;
				}
				return RealLine.getInstance().get(testValues2[1][k]);
			}

		};

		trigonometricFunctionSpace = spaceGen.getTrigonometricFunctionSpaceWithLinearGrowth(RealLine.getInstance(),
				dim);

		final EuclideanSpace trigonometricSobolevSpace = spaceGen.getFiniteDimensionalSobolevSpace(
				RealLine.getInstance(), (EuclideanFunctionSpace) trigonometricFunctionSpace, degree);

		final Vector id = new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()) {
			private static final long serialVersionUID = 1L;
		};

		sobolevSpace = trigonometricSobolevSpace;

		idToSobolevFourierCoordinates = normalizedIdentity.getProjection(sobolevSpace);

		expToSobolevFourierCoordinates = sobolevSpace.getCoordinates(exp);

		newAbs = sobolevSpace.getCoordinates(niceOne);

	}

	@Test
	public void scalarProducts() throws Throwable {
		sobolevSpace.show();
	}

	@Test
	public void identity() throws Throwable {
		normalizedIdentity.plotCompare(left, right, (Function) idToSobolevFourierCoordinates);
	}

	@Test
	public void exp() throws Throwable {
		exp.plotCompare(left, right, (Function) expToSobolevFourierCoordinates);
	}

	@Test
	public void niceone() throws Throwable {
		((Function) niceOne).plotCompare(left, right, (Function) newAbs);
	}

	@Test
	public void staircaseFunction() throws Throwable {
		staircaseFunctionToFourier = sobolevSpace.getCoordinates(staircaseFunction);
		((Function) staircaseFunction).plotCompare(left, right, (Function) staircaseFunctionToFourier);
	}

	@Test
	public void staircaseFunction2() throws Throwable {
		staircaseFunction2ToFourier = ((Function) staircaseFunction2).getProjection(sobolevSpace);
		((Function) staircaseFunction2).plotCompare(left, right, (Function) staircaseFunction2ToFourier);
	}

	protected static double[][] readFile(String string) throws IOException {
		final List<double[]> values = new ArrayList<>();
		final BufferedReader br = new BufferedReader(new FileReader(string));
		String line = "";
		LocalDate firstDate = null;
		try {
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
		} finally {
			br.close();
		}
		final double[][] ans = new double[2][values.size()];
		for (int i = 0; i < values.size(); i++) {
			ans[0][i] = values.get(i)[0];
			ans[1][i] = values.get(i)[1];
		}
		return ans;
	}

}
