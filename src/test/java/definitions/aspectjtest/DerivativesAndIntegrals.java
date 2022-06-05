package definitions.aspectjtest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.algebra.fields.Field;
import definitions.structures.abstr.algebra.fields.scalars.impl.Real;
import definitions.structures.abstr.mappings.VectorSpaceHomomorphism;
import definitions.structures.abstr.vectorspaces.InnerProductSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.mappings.impl.FiniteDimensionalDerivativeOperator;
import definitions.structures.euclidean.vectors.impl.Monome;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;

public class DerivativesAndIntegrals extends AspectJTest {

	private Sine sine;
	private Function cosine;
	private Function monome;

	private EuclideanSpace space;
	private EuclideanSpace newSpace;

	private final List<Function> testfunctions = new ArrayList<>();

	private final int degree = 5;
	private final int sobolevDegree = 1;

	private EuclideanSpace sobolevSpace;

	private VectorSpaceHomomorphism derivativeOperator;

	public Function getCosine() {
		return cosine;
	}

	public EuclideanSpace getNewSpace() {
		return newSpace;
	}

	public List<Function> getTestfunctions() {
		return testfunctions;
	}

	public void setCosine(final Function cosine) {
		this.cosine = cosine;
	}

	public void setNewSpace(final EuclideanSpace newSpace) {
		this.newSpace = newSpace;
	}

	@Before
	public void setUp() throws Throwable {

		space = (EuclideanSpace) AspectJTest.getGenerator().getTrigonometricSpace(AspectJTest.getRealLine(), degree);
		derivativeOperator = new FiniteDimensionalDerivativeOperator(space, space);

		sobolevSpace = AspectJTest.getSpaceGenerator().getTrigonometricSobolevSpaceWithLinearGrowth(
				AspectJTest.getRealLine(), degree, Math.PI, sobolevDegree);

		sine = new Sine(1, 0, 1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}
		};

		monome = new Monome(3) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return AspectJTest.getRealLine();
			}
		};

	}

	@Test
	public void scalarProducts() throws Throwable {
		final List<Vector> base = sobolevSpace.genericBaseToList();
		final double[][] scalarProducts = new double[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				scalarProducts[i][j] = ((Real) ((InnerProductSpace) sobolevSpace).innerProduct(vec1, vec2))
						.getDoubleValue();
				logger.info((scalarProducts[i][j] - (scalarProducts[i][j] % 0.001)) + ",");
				j++;
			}
			logger.info("");
			i++;
		}
	}

	@Test
	public void testLinearMonome() throws Throwable {
		AspectJTest.getLogger().info("Comparing implicite versus explicite derivative");
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(monome, 1);
		final Vector derivative2 = ((DerivativeOperator) derivativeOperator).get(monome);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, (Function) derivative2);
	}

	@Test
	public void testDerivativeOfSineInL2() throws Throwable {
		final int sobDegree = 4;
		AspectJTest.getLogger().info("Plotting " + sobDegree + "-th derivative of sine in L^2:");
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(sine, sobDegree);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);
	}

}
