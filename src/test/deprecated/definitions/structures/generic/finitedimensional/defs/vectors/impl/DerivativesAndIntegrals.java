package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.InnerProductSpace;
import definitions.structures.abstr.Scalar;
import definitions.structures.abstr.Vector;
import definitions.structures.field.scalar.Real;
import definitions.structures.finitedimensional.real.Generator;
import definitions.structures.finitedimensional.real.mappings.impl.DerivativeOperator;
import definitions.structures.finitedimensional.real.vectors.Function;
import definitions.structures.finitedimensional.real.vectors.impl.Monome;
import definitions.structures.finitedimensional.real.vectors.specialfunctions.Sine;
import definitions.structures.finitedimensional.real.vectorspaces.EuclideanSpace;

public class DerivativesAndIntegrals {

	static Sine sine;
	static Function cosine;
	static Function monome;

	static EuclideanSpace newSpace;
	final List<Function> testfunctions = new ArrayList<>();

	final static int degree = 2;
	final static int sobolevDegree = 1;

	static EuclideanSpace space = (EuclideanSpace) Generator.getGenerator().getTrigonometricSpace(degree);
	static EuclideanSpace sobolevSpace = Generator.getGenerator().getSpacegenerator()
			.getTrigonometricSobolevSpace(degree, sobolevDegree);

	static final Homomorphism derivativeOperator = new DerivativeOperator(space, space);

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {

		sine = new Sine(1, 0, 1);

		monome = new Monome(1) {
			@Override
			public Scalar value(Scalar input) {
				return new Real(1 - super.value(new Real(input.getValue() / Math.PI)).getValue());
			}
		};

	}

	@Test
	public void test() throws Throwable {
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(monome, 2);
		final Vector derivative2 = ((DerivativeOperator) derivativeOperator)
				.get(((DerivativeOperator) derivativeOperator).get(monome));

		((Function) derivative).plotCompare(-Math.PI, Math.PI, (Function) derivative2);
		final Vector sourceFun = null;

	}

	//@Test
	public void test2() throws Throwable {
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(sine, 1000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);
	}

	//@Test
	public void test3() throws Throwable {
		final Homomorphism derivativeOperatorSobToL2 = new DerivativeOperator(sobolevSpace, space);
		final Vector derivative = ((DerivativeOperator) derivativeOperatorSobToL2).get(sine, 4000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);

	}

	//@Test
	public void test4() throws Throwable {
		final Homomorphism derivativeOperatorL2ToSob = new DerivativeOperator(space, sobolevSpace);
		final Vector derivative = ((DerivativeOperator) derivativeOperatorL2ToSob).get(sine, 4000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);

	}

	//@Test
	public void test5() throws Throwable {
		final Homomorphism derivativeOperatorSobToSob = new DerivativeOperator(sobolevSpace, sobolevSpace);
		final Vector derivative = ((DerivativeOperator) derivativeOperatorSobToSob).get(sine, 4000);
		((Function) derivative).plotCompare(-Math.PI, Math.PI, sine);
	}

//	//@Test
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
}
