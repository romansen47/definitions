package definitions.structures.generic.finitedimensional.defs.vectors.impl.operators;

import definitions.structures.abstr.Homomorphism;
import definitions.structures.abstr.LinearMapping;
import definitions.structures.abstr.Vector;
import definitions.structures.abstr.VectorSpace;
import definitions.structures.generic.finitedimensional.defs.mappings.IFiniteDimensionalLinearMapping;
import definitions.structures.generic.finitedimensional.defs.mappings.impl.MappingGenerator;
import definitions.structures.generic.finitedimensional.defs.subspaces.functionspaces.IFiniteDimensionalFunctionSpace;
import definitions.structures.generic.finitedimensional.defs.vectors.Function;

public class DerivativeOperator extends LinearMapping implements IFiniteDimensionalLinearMapping {

	public DerivativeOperator(VectorSpace source, VectorSpace target) throws Throwable {
		super(source, target);
	}

	@Override
	public Vector get(Vector vec2) throws Throwable {
		return ((Function) vec2).getProjectionOfDerivative((IFiniteDimensionalFunctionSpace) target);
	}
	
	public Vector get(Vector vec2,int degree) throws Throwable {
		if (degree==0) {
			return vec2;
		}
		if (degree==1) {
			return get(vec2);
		}
		Homomorphism tmp=this;
		for (int i=0;i<degree;i++) {
			tmp=MappingGenerator.getInstance().getComposition(this, tmp);
		}
		return tmp.get(vec2);
	}

}
