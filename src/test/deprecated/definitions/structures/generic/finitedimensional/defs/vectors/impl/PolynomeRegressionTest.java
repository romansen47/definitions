package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.ArrayList;
import java.util.List;

import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.fields.scalars.impl.Real;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.functionspaces.EuclideanFunctionSpace;
import definitions.structures.euclidean.vectors.impl.GenericFunction;
import definitions.structures.euclidean.vectors.impl.Monome;

public class PolynomeRegressionTest {

//	Field realLine=RealLine.getInstance();
	final static double left = -1;
	final static double right = 1;

	final static List<Vector> base = new ArrayList<>();

	static Function sin = null;
	static Function exp = null;

	static EuclideanFunctionSpace space = null;
	static EuclideanFunctionSpace newSpace = null;

	private static int maxDegree = 3;
	protected static Field realLine;

	// @BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		sin = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// @Override
			@Override
			public Scalar value(Scalar input) {
				return new Real(Math.sin(input.getValue() * Math.PI));
			}

			@Override
			public Field getField() {
				return realLine;
			}
		};

		exp = new GenericFunction() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			// @Override
			@Override
			public Scalar value(Scalar input) {
				return new Real(Math.exp(input.getValue() * Math.PI));
			}

			@Override
			public Field getField() {
				return realLine;
			}
		};

		for (int i = 0; i < (maxDegree + 1); i++) {
			base.add(new Monome(i) {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public Field getField() {
					return realLine;
				}
			});
		}

		space = (EuclideanFunctionSpace) Generator.getGenerator()
				.getFiniteDimensionalFunctionSpace(RealLine.getInstance(), base, left, right);

//		newSpace=(IFiniteDimensionalFunctionSpace) Generator.getGenerator().getFiniteDimensionalSobolevSpace((IFiniteDimensionalFunctionSpace) space);

	}

	// @Test
	public void sinTest1() throws Throwable {
		final Function ans = sin.getProjection(space);
		ans.plotCompare(left, right, sin);
	}

	// @Test
	public void expTest1() throws Throwable {
		final Function ans = exp.getProjection(space);
		ans.plotCompare(left, right, exp);
	}

//	//@Test
	public void sinTest2() throws Throwable {
		final Function ans = sin.getProjection(newSpace);
		ans.plotCompare(left, right, sin);
	}

//	//@Test
	public void expTest2() throws Throwable {
		final Function ans = exp.getProjection(newSpace);
		ans.plotCompare(left, right, exp);
	}

}
