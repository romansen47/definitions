package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.InnerProductSpace;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.operators.DerivativeOperator;

public class DerivativesAndIntegrals {

	static Function sine;
	static Function cosine;

	static VectorSpace space;
	static VectorSpace newSpace;
	static VectorSpace sobolevSpace;
	final List<Function> testfunctions = new ArrayList<>();

	final static int degree = 10;
	final static int sobolevDegree = 5;

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		sine = new GenericFunction() {
			@Override
			public double value(double value) {
				return Math.sin(value * Math.PI);
			}
		};
		cosine = new GenericFunction() {
			@Override
			public double value(double value) {
				return Math.cos(value * Math.PI) * Math.PI;
			}
		};
		space = Generator.getGenerator().getTrigonometricSpace(degree);
		sobolevSpace = Generator.getGenerator().getSpacegenerator().getTrigonometricSobolevSpace(degree, sobolevDegree);
	}

	@Test
	public void test() throws Throwable {
		final Homomorphism derivativeOperator = new DerivativeOperator(space, space);
		final Vector derivative = derivativeOperator.get(sine);
		((Function) derivative).plotCompare(-1, 1, cosine);
	}

	@Test
	public void test2() throws Throwable {
		final Homomorphism derivativeOperator = new DerivativeOperator(space, space);
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(sine, 1);
		((Function) derivative).plotCompare(-1, 1, cosine);

	}

	@Test
	public void test3() throws Throwable {
		final Homomorphism derivativeOperator = new DerivativeOperator(newSpace, sobolevSpace);
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(sine);
		((Function) derivative).plotCompare(-1, 1, cosine);

	}

	@Test
	public void test4() throws Throwable {
		final Homomorphism derivativeOperator = new DerivativeOperator(sobolevSpace, sobolevSpace);
		final Vector derivative = ((DerivativeOperator) derivativeOperator).get(sine);
		((Function) derivative).plotCompare(-1, 1, cosine);
	}

//	@Test
	public void scalarProducts() throws Throwable {
		final List<Vector> base = ((EuclideanSpace) sobolevSpace).genericBaseToList();
		final double[][] scalarProducts = new double[base.size()][base.size()];
		int i = 0;
		for (final Vector vec1 : base) {
			int j = 0;
			for (final Vector vec2 : base) {
				scalarProducts[i][j] = ((InnerProductSpace) sobolevSpace).product(vec1, vec2);
				System.out.print((scalarProducts[i][j] - (scalarProducts[i][j] % 0.001)) + ",");
				j++;
			}
			System.out.println("");
			i++;
		}
	}
}
