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
import definitions.structures.euclidean.mappings.impl.DerivativeOperator;
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

	private final int degree = 4;
	private final int sobolevDegree = 2;

	private EuclideanSpace sobolevSpace;

	private Homomorphism derivativeOperator;

	public Function getCosine() {
		return this.cosine;
	}

	public EuclideanSpace getNewSpace() {
		return this.newSpace;
	}

	public List<Function> getTestfunctions() {
		return this.testfunctions;
	}

	@Test
	public void scalarProducts() throws Throwable {
		final List<Vector> base = this.sobolevSpace.genericBaseToList();
		final double[][] scalarProducts = new double[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				scalarProducts[i][j] = ((InnerProductSpace) this.sobolevSpace).innerProduct(vec1, vec2).getValue();
				System.out.print((scalarProducts[i][j] - (scalarProducts[i][j] % 0.001)) + ",");
				j++;
			}
			System.out.println("");
			i++;
		}
	}

	public void setCosine(final Function cosine) {
		this.cosine = cosine;
	}

	public void setNewSpace(final EuclideanSpace newSpace) {
		this.newSpace = newSpace;
	}

	@Before
	public void setUp() throws Throwable {

		this.space = (EuclideanSpace) getGenerator().getTrigonometricSpace(getRealLine(), this.degree);
		this.derivativeOperator = new DerivativeOperator(this.space, this.space);

		this.sobolevSpace = getSpaceGenerator().getTrigonometricSobolevSpace(getRealLine(), this.degree,
				this.sobolevDegree);

		this.sine = new Sine(1, 0, 1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return getRealLine();
			}
		};

		this.monome = new Monome(1) {
			private static final long serialVersionUID = 1L;

			@Override
			public Field getField() {
				return getRealLine();
			}

			@Override
			public Scalar value(final Scalar input) {
				return RealLine.getInstance()
						.get(1 - super.value(RealLine.getInstance().get(input.getValue() / Math.PI)).getValue());
			}
		};

	}

	@Test
	public void test() throws Throwable {
		getLogger().info("Comparing implicite versus explicite derivative");
		final Vector derivative = ((DerivativeOperator) this.derivativeOperator).get(this.monome, 2);
		final Vector derivative2 = ((DerivativeOperator) this.derivativeOperator)
				.get(((DerivativeOperator) this.derivativeOperator).get(this.monome));
		((Function) derivative).plotCompare(-Math.PI, Math.PI, (Function) derivative2);
	}

	@Test
	public void test2() throws Throwable {
		int sobDegree = 1000;
		getLogger().info("Plotting "+sobDegree+"-th derivative of sine in L^2:");
		final Vector derivative = ((DerivativeOperator) this.derivativeOperator).get(this.sine, degree);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, this.sine);
	}


	@Test
	public void test5() throws Throwable {
		int sobDegree = 1000;
		getLogger().info("Plotting "+sobDegree+"-th derivative of sine in H^1:");
		final Homomorphism derivativeOperatorSobToSob = new DerivativeOperator(this.sobolevSpace, this.sobolevSpace);
		final Vector derivative = ((DerivativeOperator) derivativeOperatorSobToSob).get(this.sine, sobDegree);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, this.sine);
	}
}
