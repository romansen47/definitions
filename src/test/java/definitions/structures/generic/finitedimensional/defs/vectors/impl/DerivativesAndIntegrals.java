package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;
import definitions.structures.generic.finitedimensional.defs.vectors.impl.operators.DerivativeOperator;

public class DerivativesAndIntegrals {

	static Function sine;

	static Function cosine;

	static VectorSpace space;

	final List<Function> testfunctions = new ArrayList<>();

	final static int degree = 20;

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
	}

//	@Test
	public void test() throws Throwable {
		Homomorphism derivativeOperator = new DerivativeOperator(space, space);
		Vector derivative = derivativeOperator.get(sine);
		((Function)derivative).plotCompare(-1,1,cosine);
		Map<Vector, Map<Vector, Double>> test = derivativeOperator.getLinearity();
		int i = 0;
	}
	
	@Test
	public void test2() throws Throwable {
		Homomorphism derivativeOperator = new DerivativeOperator(space, space);
		Vector derivative = ((DerivativeOperator)derivativeOperator).get(sine,5);
		((Function)derivative).plotCompare(-1,1,cosine);//(Function) space.stretch(sine,-1));
		
	}

}
