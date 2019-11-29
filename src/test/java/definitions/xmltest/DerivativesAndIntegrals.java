package definitions.xmltest;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import definitions.prototypes.AspectJTest;
import definitions.structures.abstr.fields.Field;
import definitions.structures.abstr.fields.impl.RealLine;
import definitions.structures.abstr.fields.scalars.Scalar;
import definitions.structures.abstr.mappings.Homomorphism;
import definitions.structures.abstr.vectorspaces.InnerProductSpace;
import definitions.structures.abstr.vectorspaces.vectors.Function;
import definitions.structures.abstr.vectorspaces.vectors.Vector;
import definitions.structures.euclidean.Generator;
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
import definitions.structures.euclidean.vectors.impl.Monome;
import definitions.structures.euclidean.vectors.specialfunctions.Sine;
import definitions.structures.euclidean.vectorspaces.EuclideanSpace;
import definitions.structures.euclidean.vectorspaces.impl.SpaceGenerator;

public class DerivativesAndIntegrals extends AspectJTest {

	private Sine sine;
	private Function cosine;
	private Function monome;

	private EuclideanSpace space;
	private EuclideanSpace newSpace;

	private final List<Function> testfunctions = new ArrayList<>();

	private final int degree = 4;
	private final int sobolevDegree = 2;

	private EuclideanSpace sobolevSpace;

	private Homomorphism derivativeOperator;

	@Before
	public void setUp() throws Throwable {

		space = (EuclideanSpace) getGenerator().getTrigonometricSpace(getRealLine(), degree);
		derivativeOperator = new DerivativeOperator(space, space);

		sobolevSpace = getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(), degree, sobolevDegree);

		sine = new Sine(1, 0, 1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return getRealLine();
			}
		};

		monome = new Monome(1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Scalar value(Scalar input) {
				return RealLine.getInstance()
						.get(1 - super.value(RealLine.getInstance().get(input.getValue() / Math.PI)).getValue());
			}

			@Override
			public Field getField() {
				return getRealLine();
			}
		};

	}

	@Test
	public void test() throws Throwable {
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(monome, 2);
		final Vector derivative2 = ((DerivativeOperator) derivativeOperator)
				.get(((DerivativeOperator) derivativeOperator).get(monome));

		((Function) derivative).plotCompare(-Math.PI, Math.PI, (Function) derivative2);
	}

	@Test
	public void test2() throws Throwable {
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(sine, 1000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);
	}

//	 @Test
	public void test3() throws Throwable {
		final Homomorphism derivativeOperatorSobToL2 = new DerivativeOperator(sobolevSpace, space);
		final Vector derivative = ((DerivativeOperator) derivativeOperatorSobToL2).get(sine, 4000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);

	}

//	 @Test
	public void test4() throws Throwable {
		final Homomorphism derivativeOperatorL2ToSob = new DerivativeOperator(space, sobolevSpace);
		final Vector derivative = ((DerivativeOperator) derivativeOperatorL2ToSob).get(sine, 4000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);

	}

//	@Test
	public void test5() throws Throwable {
		final Homomorphism derivativeOperatorSobToSob = new DerivativeOperator(sobolevSpace, sobolevSpace);
		final Vector derivative = ((DerivativeOperator) derivativeOperatorSobToSob).get(sine, 4000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);
	}

	 @Test
	public void scalarProducts() throws Throwable {
		final List<Vector> base = sobolevSpace.genericBaseToList();
		final double[][] scalarProducts = new double[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				scalarProducts[i][j] = ((InnerProductSpace) sobolevSpace).innerProduct(vec1, vec2).getValue();
				System.out.print((scalarProducts[i][j] - (scalarProducts[i][j] % 0.001)) + ",");
				j++;
			}
			System.out.println("");
			i++;
		}
	}

	public Function getCosine() {
		return cosine;
	}

	public void setCosine(Function cosine) {
		this.cosine = cosine;
	}

	public EuclideanSpace getNewSpace() {
		return newSpace;
	}

	public void setNewSpace(EuclideanSpace newSpace) {
		this.newSpace = newSpace;
	}

	public List<Function> getTestfunctions() {
		return testfunctions;
	}
}
