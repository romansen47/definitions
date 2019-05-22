package definitions.structures.generic.finitedimensional.defs.mappings;

import java.util.HashMap;
import java.util.Map;

import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.spaces.IFiniteDimensionalVectorSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.IFiniteDimensionalSubSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.FiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionalspaces.IFunction;
import definitions.structures.generic.finitedimensional.defs.vectors.IFiniteVector;

public class FunctionSpaceOperator implements IFiniteDimensionalInjectiveLinearMapping {

	final IFiniteDimensionalFunctionSpace source;
	final IFiniteDimensionalFunctionSpace target;
	private double[][] genericMatrix;
	private Map<IFiniteVector, Map<IFiniteVector, Double>> linearity;
	
	public FunctionSpaceOperator(IFiniteDimensionalFunctionSpace source,
			IFiniteDimensionalFunctionSpace target,
			Map<IFiniteVector, Map<IFiniteVector, Double>> linearity
			) throws Throwable {
		this.source=source;
		this.target=target;
		this.linearity=linearity;
		genericMatrix = new double[target.dim()][source.dim()];
		int i = 0;
		for (final Vector vec1 : source.genericBaseToList()) {
			int j = 0;
			for (final Vector vec2 : target.genericBaseToList()) {
				genericMatrix[j][i] = target.product(vec1,vec2);
				j++;
			}
			i++;
		}
	}
	
	@Override
	public IFiniteVector solve(IFiniteVector image) throws Throwable {
		return null;
	}

	@Override
	public IFiniteDimensionalVectorSpace getSource() {
		return source;
	}

	@Override
	public VectorSpace getTarget() {
		return target;
	}

	@Override
	public Map<IFiniteVector, Map<IFiniteVector, Double>> getLinearity() {
		return linearity;
	}

	@Override
	public double[][] getGenericMatrix() throws Throwable {
		return genericMatrix;
	}
	
}
