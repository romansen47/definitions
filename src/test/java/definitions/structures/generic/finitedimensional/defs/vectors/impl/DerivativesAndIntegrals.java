package definitions.structures.generic.finitedimensional.defs.vectors.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.BeforeClass;
import org.junit.Test;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.Generator;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.FunctionSpaceOperator;
import definitions.structures.generic.finitedimensional.defs.spaces.EuclideanSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public class DerivativesAndIntegrals {

	static Function sine;

	static VectorSpace space;

	final List<Function> testfunctions = new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Throwable {
		sine = new GenericFunction() {
			@Override
			public double value(double value) {
				return Math.sin(value*Math.PI);
			}
		};
		space=Generator.getGenerator().getTrigonometricSpace(20);
	}

	@Test
	public void test() throws Throwable {
		Function asTuple=sine.getProjection((EuclideanSpace) space);		
		Function derivative=asTuple.getProjectionOfDerivative((IFiniteDimensionalFunctionSpace) space);
		Homomorphism derivativeOperator=new LinearMapping(space, space) {
			@Override
			public Vector get(Vector vec2) throws Throwable {
				return ((Function)vec2).getProjectionOfDerivative((IFiniteDimensionalFunctionSpace) space);
			}

			@Override
			public Map<Vector, Double> getLinearity(Vector vec1) throws Throwable {
				return null;
			}
		};
		((Function)derivativeOperator.get(asTuple)).plotCompare(-1,1,derivative);
		
	}
	
}
