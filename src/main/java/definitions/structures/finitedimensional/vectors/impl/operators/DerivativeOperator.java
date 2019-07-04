package definitions.structures.finitedimensional.vectors.impl.operators;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.finitedimensional.functionspaces.EuclideanFunctionSpace;
import definitions.structures.finitedimensional.mappings.FiniteDimensionalHomomorphism;
import definitions.structures.finitedimensional.mappings.impl.MappingGenerator;
import definitions.structures.finitedimensional.vectors.Function;

public class DerivativeOperator extends LinearMapping implements FiniteDimensionalHomomorphism {

	public DerivativeOperator(VectorSpace source, VectorSpace target) {
		super(source, target);
	}

	@Override
	public Vector get(Vector vec2) {
		return ((Function) vec2).getProjectionOfDerivative((EuclideanFunctionSpace) this.target);
	}

	public Vector get(Vector vec2, int degree) {
		if (degree == 0) {
			return vec2;
		}
		if (degree == 1) {
			return this.get(vec2);
		}
		Homomorphism tmp = this;
		for (int i = 0; i < degree; i++) {
			tmp = MappingGenerator.getInstance().getComposition(this, tmp);
		}
		return tmp.get(vec2);
	}

}
