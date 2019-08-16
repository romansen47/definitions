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

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.VectorSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.specialfunctions.ExponentialFunction;
import definitions.structures.euclidean.vectors.specialfunctions.LinearFunction;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.ISpaceGenerator;

public class FiniteDimensionalSobolevSpaceTest {

	final static VectorSpace realLine = RealLine.getInstance();
	static EuclideanFunctionSpace trigonometricFunctionSpace;
	static EuclideanSpace sobolevSpace;
	final static ISpaceGenerator spaceGen = Generator.getGenerator().getSpacegenerator();

	static double left = -Math.PI;
	static double right = Math.PI;

	static final int dim = 3;

	static final int degree = 2;

	static Function normalizedIdentity;
	static Function exp;

	protected static final String PATH = "src/main/resources/test.csv";
	protected static final String PATH2 = "src/main/resources/test2.csv";

	protected static double[][] testValues;
	protected static double[][] testValues2;

	static Vector abs;
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
			final double norm = Math.sqrt(2 * Math.PI) + Math.sqrt((2 * Math.pow(Math.PI, 3)) / 3);

			@Override
			public Scalar value(Scalar input) {
				return new Real(input.getValue() / this.norm);
			}

			@Override
			public Field getField() {
				// TODO Auto-generated method stub
				return (Field)realLine;
			}
		};
		exp = new ExponentialFunction((Scalar) realLine.nullVec(), ((RealLine) realLine).getOne()) {

			@Override
			public Field getField() {
				// TODO Auto-generated method stub
				return null;
			}};
		abs = new GenericFunction() {
			@Override
			public Scalar value(Scalar input) {
				return new Real(Math.abs(input.getValue()));
			}

			@Override
			public Field getField() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		staircaseFunction = new GenericFunction() {
			int length = (int) testValues[0][testValues[0].length - 1];

			@Override
			public Scalar value(Scalar input) {
				final double newInput = ((this.length / (2 * Math.PI)) * input.getValue()) + (this.length / 2.);
				int k = 0;
				final int l = (int) (newInput - (newInput % 1));
				while (testValues[0][k] < l) {
					k++;
				}
				return new Real(testValues[1][k]);
			}

			@Override
			public Field getField() {
				// TODO Auto-generated method stub
				return (Field)realLine;
			}
		};
		staircaseFunction2 = new GenericFunction() {
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

			@Override
			public Field getField() {
				// TODO Auto-generated method stub
				return null;
			}
		};

		final ISpaceGenerator generator = Generator.getGenerator().getSpacegenerator();

		trigonometricFunctionSpace = generator.getTrigonometricFunctionSpaceWithLinearGrowth(RealLine.getInstance(),
				dim);
//				.getTrigonometricSpace(RealLine.getInstance(), dim);

		final EuclideanSpace trigonometricSobolevSpace = generator.getTrigonometricSobolevSpace(RealLine.getInstance(),
				dim, degree);
		final Vector id = new LinearFunction(RealLine.getInstance().getZero(), RealLine.getInstance().getOne()) {

			@Override
			public Field getField() {
				// TODO Auto-generated method stub
				return (Field)realLine;
			}};
		sobolevSpace = generator.extend(trigonometricSobolevSpace, id);

		idToSobolevFourierCoordinates = normalizedIdentity.getProjection(sobolevSpace);

		expToSobolevFourierCoordinates = sobolevSpace.getCoordinates(exp);

		newAbs = sobolevSpace.getCoordinates(abs);

	}

	@Test
	public void test1() throws Throwable {
		for (final Vector vec : sobolevSpace.genericBaseToList()) {
			((Function) vec).plot(left, right);
			((Function) vec).getDerivative().plot(left, right);
		}
	}

//	@Test
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
	public void abs() throws Throwable {
		((Function) abs).plotCompare(left, right, (Function) newAbs);
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
